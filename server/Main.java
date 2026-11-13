import gui.LoginFrame;
import gui.Theme;
import models.Diary;
import models.Transaction;
import managers.DiaryManager;
import managers.TransactionManager;
import managers.UserManager;
import repositories.Database;
import repositories.DiaryRepository;
import repositories.TransactionRepository;
import repositories.UserRepository;
import models.User;
import utils.ReportGenerator;
import javax.swing.*;
import java.sql.*;


public class Main {
    public static void main(String[] args) {
         SwingUtilities.invokeLater(() -> {
             Theme.install();

             try {
                 Class.forName("org.sqlite.JDBC");   // loads the SQLite driver
             } catch (ClassNotFoundException e) {
                 JOptionPane.showMessageDialog(null,
                         "SQLite jar is not on the classpath.\n"
                                 + "Add lib/sqlite-jdbc-3.53.4.0.jar to the module dependencies.",
                         "Startup Error", JOptionPane.ERROR_MESSAGE);
                 return;
             }

             try {
                 Database.createTables();   // creates tables if they don't exist yet
             } catch (SQLException e) {
                 JOptionPane.showMessageDialog(null,
                         "Could not open the database:\n" + e.getMessage(),
                         "Startup Error", JOptionPane.ERROR_MESSAGE);
                 return;
             }

             UserManager userManager = new UserManager(new UserRepository());
             new LoginFrame(userManager).setVisible(true);
         });

//        UserManager userManager = new UserManager(new UserRepository());
//        userManager.addNewUser("Rohit", "RRR@123");
//
//        User user = userManager.authenticateUser(new User("Rohit", "RRR@123"));
//
//        DiaryManager diaryManager = new DiaryManager(user, new DiaryRepository());
//        // diaryManager.addNewDiary(2, 2026, 2000, 4500);
//
//        Diary diary = diaryManager.openDiary(2, 2026);
//
//        TransactionManager transactionManager = new TransactionManager(diaryManager, new TransactionRepository());
//        transactionManager.handleIncm(diary, 400, "11/02/2026", "" );
//
//        try {
//            ReportGenerator.generateReport(diary);
//        } catch (Exception e) {
//            System.out.println(e);
//        }
    }
}