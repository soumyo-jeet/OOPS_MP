import gui.LoginFrame;
import gui.Theme;
import managers.UserManager;
import repositories.Database;
import repositories.UserRepository;

import javax.swing.*;
import java.sql.SQLException;

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
    }
}