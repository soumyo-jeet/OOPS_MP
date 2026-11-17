# Expense Tracker & Personal Diary

A desktop Java application for tracking personal finances and maintaining a daily journal. Built with Java Swing for the interface, SQLite for local data storage, and iText 7 for exporting monthly PDF reports.

## Features

- **User Accounts:** Basic login and registration system (`LoginFrame`, `RegisterDialog`) backed by SQLite so multiple users can keep their data separate.
- **Expense & Income Tracking:** Log daily transactions, categorize them, and view current balances.
- **Personal Diary:** Write, save, and browse dated journal entries tied to your account.
- **PDF Exports:** Generate monthly summaries combining financial logs and diary entries using iText 7 (`ReportGenerator`). Reports are saved directly to the `reports/` folder.

## Dependencies

All required `.jar` files are included in the `lib/` directory:
- **SQLite JDBC** (`sqlite-jdbc-3.53.4.0.jar`) – Database connectivity
- **iText 7** (`kernel`, `layout`, `io`, `commons`, `barcodes`) – PDF generation
- **SLF4J** (`slf4j-api-1.7.13.jar`) – Logging facade required by dependencies

## Directory Layout

```text
OOPS_MP-main/
├── database/
│   └── expense_tracker.db      # Local SQLite database
├── gui/                        # Swing UI frames, dialogs, and styling
│   ├── LoginFrame.java
│   ├── MainFrame.java
│   ├── RegisterDialog.java
│   └── Theme.java
├── lib/                        # Third-party JARs
├── reports/                    # Output folder for generated PDFs
└── server/                     # Core application logic
    ├── Main.java               # Entry point
    ├── managers/               # Business logic (User, Transaction, Diary)
    ├── models/                 # Data models (User, Transaction, Income, Expense, Diary)
    ├── repositories/           # SQLite queries and DB connection
    └── utils/                  # PDF generator and enums
```

## Getting Started

### Prerequisites
- JDK 8 or higher

### Running in IntelliJ IDEA
1. Open the `OOPS_MP-main` folder in IntelliJ IDEA.
2. Go to **File > Project Structure > Libraries** and ensure all `.jar` files in `lib/` are added to the classpath.
3. Open `server/Main.java` and run `Main.main()`.

### Running from the Terminal

Navigate to the root directory (`OOPS_MP-main`) and compile the source files:

```bash
javac -cp "..\lib\*" -sourcepath ".;.." -d bin Main.java
```

Run the compiled application:

**Windows:**
```bash
java -cp "bin;..\lib\*" Main
```


## Architecture Notes

The project follows a layered structure separating the Swing UI (`gui/`) from the backend logic (`server/`):
- **Models:** `Income` and `Expense` inherit from the base `Transaction` class to handle type-specific behavior.
- **Repositories:** Encapsulate raw SQLite queries and database connection management (`Database.java`).
- **Managers:** Act as an intermediary layer between the UI events and the database repositories.