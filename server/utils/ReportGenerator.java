package utils;

import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import models.Diary;
import models.Transaction;
import repositories.TransactionRepository;

public class ReportGenerator {
    private static final TransactionRepository transactionRepository = new TransactionRepository();

    public static Path generateReport(Diary diary) throws IOException {
        if (diary == null) {
            throw new IllegalArgumentException("Diary must not be null.");
        }
        if (diary.getId() <= 0) {
            throw new IllegalArgumentException("Diary must have a valid ID.");
        }
        if (diary.getMon() < 1 || diary.getMon() > 12) {
            throw new IllegalArgumentException("Diary month must be between 1 and 12.");
        }

        List<Transaction> transactions = transactionRepository.findAllByDiary(diary);
        Path reportDirectory = Paths.get("reports");
        Files.createDirectories(reportDirectory);

        String fileName = String.format(
                Locale.ROOT,
                "diary-%d-%04d-%02d.pdf",
                diary.getId(),
                diary.getYear(),
                diary.getMon());
        Path reportPath = reportDirectory.resolve(fileName);

        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
        numberFormat.setMinimumFractionDigits(2);
        numberFormat.setMaximumFractionDigits(2);

        double totalIncome = 0;
        double totalExpenses = 0;
        for (Transaction transaction : transactions) {
            if (transaction.getType() == TransactionTypeEnum.INCOME) {
                totalIncome += transaction.getAmount();
            } else if (transaction.getType() == TransactionTypeEnum.EXPENSE) {
                totalExpenses += transaction.getAmount();
            }
        }

        try (Document document = new Document(
                new PdfDocument(new PdfWriter(reportPath.toFile())),
                PageSize.A4)) {
            document.setMargins(36, 36, 36, 36);

            String monthName = Month.of(diary.getMon())
                    .getDisplayName(TextStyle.FULL, Locale.getDefault());
            document.add(new Paragraph("Diary Report")
                    .setFontSize(20)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph(monthName + " " + diary.getYear())
                    .setFontSize(14)
                    .setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("Diary ID: " + diary.getId())
                    .setFontSize(10)
                    .setTextAlignment(TextAlignment.CENTER));

            document.add(new Paragraph("Diary Summary")
                    .setFontSize(14)
                    .setBold()
                    .setMarginTop(18));

            Table summary = new Table(UnitValue.createPercentArray(new float[] { 1, 1 }))
                    .useAllAvailableWidth();
            addSummaryRow(summary, "Expected income", numberFormat.format(diary.getExpIncome()));
            addSummaryRow(summary, "Budget", numberFormat.format(diary.getBudget()));
            addSummaryRow(summary, "Remaining budget", numberFormat.format(diary.getBudgetLeft()));
            addSummaryRow(summary, "Balance", numberFormat.format(diary.getBalance()));
            addSummaryRow(summary, "Transaction income total", numberFormat.format(totalIncome));
            addSummaryRow(summary, "Transaction expense total", numberFormat.format(totalExpenses));
            document.add(summary);

            document.add(new Paragraph("Transactions")
                    .setFontSize(14)
                    .setBold()
                    .setMarginTop(18));

            Table transactionTable = new Table(
                    UnitValue.createPercentArray(new float[] { 0.8f, 1.1f, 1.1f, 1.3f, 3.7f }))
                    .useAllAvailableWidth();
            addHeaderCell(transactionTable, "ID");
            addHeaderCell(transactionTable, "Date");
            addHeaderCell(transactionTable, "Type");
            addHeaderCell(transactionTable, "Amount");
            addHeaderCell(transactionTable, "Description");

            if (transactions.isEmpty()) {
                transactionTable.addCell(new Cell(1, 5)
                        .add(new Paragraph("No transactions recorded for this diary."))
                        .setTextAlignment(TextAlignment.CENTER));
            } else {
                for (Transaction transaction : transactions) {
                    transactionTable.addCell(String.valueOf(transaction.getId()));
                    transactionTable.addCell(
                            transaction.getDate() == null ? "" : transaction.getDate());
                    transactionTable.addCell(
                            transaction.getType() == null
                                    ? "Unknown"
                                    : transaction.getType().name());
                    transactionTable.addCell(numberFormat.format(transaction.getAmount()));
                    transactionTable.addCell(
                            transaction.getMsg() == null || transaction.getMsg().isBlank()
                                    ? ""
                                    : transaction.getMsg());
                }
            }
            document.add(transactionTable);
        }

        return reportPath.toAbsolutePath();
    }

    private static void addSummaryRow(Table table, String label, String value) {
        table.addCell(new Cell().add(new Paragraph(label).setBold()));
        table.addCell(new Cell().add(new Paragraph(value))
                .setTextAlignment(TextAlignment.RIGHT));
    }

    private static void addHeaderCell(Table table, String text) {
        table.addHeaderCell(new Cell()
                .add(new Paragraph(text).setBold().setFontColor(new DeviceRgb(255, 255, 255)))
                .setBackgroundColor(new DeviceRgb(45, 80, 120)));
    }
}
