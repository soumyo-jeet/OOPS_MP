package repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.File;

public class Database {
    private static final String URL =
            "jdbc:sqlite:" + findDatabaseFile();
    private static String findDatabaseFile() {
        String[] candidates = {
                "database/expense_tracker.db",
                "../database/expense_tracker.db"
        };
        for (String path : candidates) {
            File f = new File(path);
            if (f.exists()) {
                return f.getAbsolutePath();
            }
        }
        // Not found: use the most likely location so the error message shows the path
        return new File("../database/expense_tracker.db").getAbsolutePath();
    }


    public static Connection getConnection()
        throws SQLException {

    Connection con = DriverManager.getConnection(URL);

    try (Statement st = con.createStatement()) {
        st.execute("PRAGMA foreign_keys = ON");
    }

    System.out.println("Connection Successful.");

    return con;
}

public static void createTables() throws SQLException {

    String users = """
        CREATE TABLE IF NOT EXISTS users (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            username TEXT NOT NULL UNIQUE,
            password TEXT NOT NULL
        )
        """;

    String diaries = """
        CREATE TABLE IF NOT EXISTS diaries (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            user_id INTEGER NOT NULL,
            month INTEGER NOT NULL CHECK(month BETWEEN 1 AND 12),
            year INTEGER NOT NULL,
            expected_income REAL NOT NULL,
            budget REAL NOT NULL CHECK(budget >= 0),
            budget_left REAL NOT NULL,
            balance REAL NOT NULL DEFAULT 0,

            FOREIGN KEY (user_id)
                REFERENCES users(id)
                ON DELETE CASCADE,

            UNIQUE(user_id, month, year)
        )
        """;

    String transactions = """
        CREATE TABLE IF NOT EXISTS transactions (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            diary_id INTEGER NOT NULL,
            amount REAL NOT NULL CHECK(amount > 0),
            date TEXT NOT NULL,
            message TEXT,
            type TEXT NOT NULL
                CHECK(type IN ('INCOME', 'EXPENSE')),

            FOREIGN KEY (diary_id)
                REFERENCES diaries(id)
                ON DELETE CASCADE
        )
        """;

    try (Connection con = getConnection();
         Statement st = con.createStatement()) {

        st.execute(users);
        st.execute(diaries);
        st.execute(transactions);

        System.out.println("Tables created successfully.");
    }
}
}
