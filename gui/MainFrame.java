package gui;

import managers.DiaryManager;
import managers.TransactionManager;
import managers.UserManager;
import models.Diary;
import models.Transaction;
import models.User;
import repositories.DiaryRepository;
import repositories.TransactionRepository;
import utils.TransactionTypeEnum;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class MainFrame extends JFrame {

    private final UserManager userManager;
    private final DiaryManager diaryManager;
    private final TransactionManager transactionManager;

    private Diary currentDiary = null;   // the diary the user has opened

    // Diary inputs
    private final JComboBox<Integer> monthBox = new JComboBox<>();
    private final JTextField yearField = field(6);
    private final JTextField expIncomeField = field(10);
    private final JTextField budgetField = field(10);
    private final JLabel diaryInfo = Theme.muted("No diary open.");

    // Transaction inputs
    private final JComboBox<TransactionTypeEnum> typeBox = new JComboBox<>(TransactionTypeEnum.values());
    private final JTextField amountField = field(10);
    private final JTextField dateField = field(10);
    private final JTextField messageField = field(18);

    // Transaction table
    private final DefaultTableModel tableModel =
            new DefaultTableModel(new String[]{"Type", "Amount", "Date", "Message"}, 0) {
                @Override
                public boolean isCellEditable(int row, int col) {
                    return false;   // read-only table
                }
            };
    private final JTable table = new JTable(tableModel);

    public MainFrame(User user, UserManager userManager) {
        this.userManager = userManager;
        this.diaryManager = new DiaryManager(user, new DiaryRepository());
        this.transactionManager = new TransactionManager(diaryManager, new TransactionRepository());

        setTitle("Expense Tracker - " + user.getUsername());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 680);
        setMinimumSize(new Dimension(900, 600));
        setLocationRelativeTo(null);

        // Fill the month list and pre-fill today's values
        for (int m = 1; m <= 12; m++) {
            monthBox.addItem(m);
        }
        monthBox.setSelectedItem(LocalDate.now().getMonthValue());
        yearField.setText(String.valueOf(LocalDate.now().getYear()));
        dateField.setText(LocalDate.now().toString());

        table.setRowHeight(26);
        table.setFont(Theme.BODY);

        JPanel root = new JPanel(new BorderLayout(0, 16));
        root.setBackground(Theme.BACKGROUND);
        root.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        root.add(buildHeader(user), BorderLayout.NORTH);
        root.add(buildBody(), BorderLayout.CENTER);
        setContentPane(root);
    }

    // ---------- Layout ----------

    private JPanel buildHeader(User user) {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JLabel title = new JLabel("Welcome, " + user.getUsername());
        title.setFont(Theme.TITLE);
        title.setForeground(Theme.TEXT);

        JButton logout = Theme.linkButton("Log out");
        logout.addActionListener(e -> logout());

        header.add(title, BorderLayout.WEST);
        header.add(logout, BorderLayout.EAST);
        return header;
    }

    private JPanel buildBody() {
        JPanel body = new JPanel(new BorderLayout(0, 16));
        body.setOpaque(false);
        body.add(buildDiaryCard(), BorderLayout.NORTH);
        body.add(buildTransactionCard(), BorderLayout.CENTER);
        return body;
    }

    private JPanel buildDiaryCard() {
        JPanel card = card(new BorderLayout(0, 12));

        JPanel form = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        form.setOpaque(false);
        form.add(Theme.label("Month"));
        form.add(monthBox);
        form.add(Theme.label("Year"));
        form.add(yearField);
        form.add(Theme.label("Expected income"));
        form.add(expIncomeField);
        form.add(Theme.label("Budget"));
        form.add(budgetField);

        JButton createButton = Theme.primaryButton("Create Diary");
        JButton openButton = Theme.primaryButton("Open Diary");
        createButton.addActionListener(e -> createDiary());
        openButton.addActionListener(e -> openDiary());
        form.add(createButton);
        form.add(openButton);

        card.add(form, BorderLayout.NORTH);
        card.add(diaryInfo, BorderLayout.CENTER);
        return card;
    }

    private JPanel buildTransactionCard() {
        JPanel card = card(new BorderLayout(0, 12));

        JPanel form = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        form.setOpaque(false);
        form.add(Theme.label("Type"));
        form.add(typeBox);
        form.add(Theme.label("Amount"));
        form.add(amountField);
        form.add(Theme.label("Date"));
        form.add(dateField);
        form.add(Theme.label("Message"));
        form.add(messageField);

        JButton addButton = Theme.primaryButton("Add Transaction");
        addButton.addActionListener(e -> addTransaction());
        form.add(addButton);

        card.add(form, BorderLayout.NORTH);
        card.add(new JScrollPane(table), BorderLayout.CENTER);
        return card;
    }

    // ---------- Button actions ----------

    private void createDiary() {
        try {
            int month = (Integer) monthBox.getSelectedItem();
            int year = Integer.parseInt(yearField.getText().trim());
            double expIncome = Double.parseDouble(expIncomeField.getText().trim());
            double budget = Double.parseDouble(budgetField.getText().trim());

            diaryManager.addNewDiary(month, year, expIncome, budget);
            currentDiary = diaryManager.openDiary(month, year);
            refreshScreen();
        } catch (NumberFormatException ex) {
            showError("Year, expected income and budget must be numbers.");
        } catch (RuntimeException ex) {
            showError(ex.getMessage());
        }
    }

    private void openDiary() {
        try {
            int month = (Integer) monthBox.getSelectedItem();
            int year = Integer.parseInt(yearField.getText().trim());

            currentDiary = diaryManager.openDiary(month, year);
            refreshScreen();
        } catch (NumberFormatException ex) {
            showError("Year must be a number.");
        } catch (RuntimeException ex) {
            showError(ex.getMessage());
        }
    }

    private void addTransaction() {
        if (currentDiary == null) {
            showError("Create or open a diary first.");
            return;
        }

        try {
            double amount = Double.parseDouble(amountField.getText().trim());
            String date = dateField.getText().trim();
            String message = messageField.getText().trim();
            TransactionTypeEnum type = (TransactionTypeEnum) typeBox.getSelectedItem();

            if (type == TransactionTypeEnum.EXPENSE) {
                transactionManager.handleExp(currentDiary, amount, date, message);
            } else {
                transactionManager.handleIncm(currentDiary, amount, date, message);
            }

            amountField.setText("");
            messageField.setText("");
            refreshScreen();
        } catch (NumberFormatException ex) {
            showError("Amount must be a number.");
        } catch (RuntimeException ex) {
            showError(ex.getMessage());
        }
    }

    private void logout() {
        new LoginFrame(userManager).setVisible(true);
        dispose();
    }

    // ---------- Refreshing the screen ----------

    /** Redraws the summary line and the transaction table from the current diary. */
    private void refreshScreen() {
        tableModel.setRowCount(0);

        if (currentDiary == null) {
            diaryInfo.setText("No diary open. Pick a month and year, then create or open a diary.");
            return;
        }

        diaryInfo.setText(String.format(
                "Diary %d/%d     Expected income: %.2f     Budget: %.2f     Budget left: %.2f     Balance: %.2f",
                currentDiary.getMon(), currentDiary.getYear(), currentDiary.getExpIncome(),
                currentDiary.getBudget(), currentDiary.getBudgetLeft(), currentDiary.getBalance()));

        List<Transaction> txns = transactionManager.listAllTxs(currentDiary);
        for (Transaction t : txns) {
            tableModel.addRow(new Object[]{
                    t.getType().name(),
                    String.format("%.2f", t.getAmount()),
                    t.getDate(),
                    t.getMsg()
            });
        }
    }

    // ---------- Small helpers ----------

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private static JPanel card(LayoutManager layout) {
        JPanel panel = new JPanel(layout);
        panel.setBackground(Theme.CARD);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Theme.BORDER),
                BorderFactory.createEmptyBorder(16, 16, 16, 16)));
        return panel;
    }

    private static JTextField field(int columns) {
        JTextField f = Theme.styleField(new JTextField());
        f.setColumns(columns);
        return f;
    }
}