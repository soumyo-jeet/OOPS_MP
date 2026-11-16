# Personal Expense Tracker & Diary Management System

A desktop Java application built using Object-Oriented Programming (OOP) principles, Java Swing for the graphical user interface, SQLite for persistent local storage, and iText 7 for generating PDF reports. 

This application allows users to manage their daily financial transactions (Income and Expenses) alongside personal diary entries, complete with user authentication and automated monthly PDF reporting.

---

## 🚀 Features

* **User Authentication:** Secure login and registration system (`LoginFrame`, `RegisterDialog`) managed via `UserManager` and `UserRepository`.
* **Expense & Income Tracking:** Categorize, add, view, and manage financial transactions (`Income`, `Expense`, `Transaction`) with real-time balance calculations.
* **Personal Diary:** Write, store, and review daily journal entries (`Diary`) linked to specific user accounts.
* **PDF Report Generation:** Export monthly financial summaries and diary logs directly to PDF format using the iText 7 library (`ReportGenerator`).
* **Custom UI Theme:** Clean and modern desktop interface powered by Java Swing with custom styling (`Theme.java`).
* **Local Database Persistence:** Lightweight, serverless data storage using SQLite (`expense_tracker.db`).

---

## 🛠️ Tech Stack & Dependencies

* **Language:** Java (JDK 8 or higher)
* **GUI Framework:** Java Swing / AWT
* **Database:** SQLite (`sqlite-jdbc-3.53.4.0.jar`)
* **PDF Generation:** iText 7 (`kernel-7.2.5.jar`, `layout-7.2.5.jar`, `io-7.1.4.jar`, `commons-7.2.5.jar`, `barcodes-7.1.4.jar`)
* **Logging:** SLF4J (`slf4j-api-1.7.13.jar`)

---

## 📁 Project Structure

```text
OOPS_MP-main/
├── database/
│   └── expense_tracker.db          # SQLite database storing users, transactions, and diary entries
├── gui/
│   ├── LoginFrame.java             # Application entry login window
│   ├── MainFrame.java              # Main dashboard for expenses, income, and diary
│   ├── RegisterDialog.java         # New user registration modal
│   └── Theme.java                  # UI color palette, fonts, and styling constants
├── lib/                            # External JAR dependencies (SQLite JDBC, iText7, SLF4J)
├── reports/                        # Generated PDF reports output directory
└── server/                         # Core backend logic (MVC/Repository Architecture)
    ├── Main.java                   # Application entry point
    ├── managers/                   # Business logic layer
    │   ├── DiaryManager.java
    │   ├── TransactionManager.java
    │   └── UserManager.java
    ├── models/                     # Data entities & OOP models
    │   ├── Diary.java
    │   ├── Expense.java
    │   ├── Income.java
    │   ├── Transaction.java
    │   └── User.java
    ├── repositories/               # Data Access Objects (DAO) & SQLite connection
    │   ├── Database.java
    │   ├── DiaryRepository.java
    │   ├── TransactionRepository.java
    │   └── UserRepository.java
    └── utils/                      # Helper classes & enums
        ├── ReportGenerator.java
        └── TransactionTypeEnum.java
```

---

## ⚙️ Setup & Installation

### Prerequisites
* **Java Development Kit (JDK):** Version 8 or higher installed on your system.
* **IDE (Recommended):** IntelliJ IDEA (project includes `.idea` and `OOPS_MP.iml` configurations) or Eclipse.

### Running via IntelliJ IDEA
1. Clone or extract the `OOPS_MP-main` repository.
2. Open IntelliJ IDEA and select **Open**, then choose the `OOPS_MP-main` folder.
3. Ensure all `.jar` files inside the `lib/` directory are added to your Project Structure (**File > Project Structure > Libraries**).
4. Locate `server/Main.java`, right-click, and select **Run 'Main.main()'**.

### Running via Command Line (Terminal)

1. **Navigate to the project root directory:**
   ```bash
   cd OOPS_MP-main
   ```

2. **Compile the source code:**
   * **On Windows:**
     ```cmd
     javac -d out -cp "lib/*" gui/*.java server/*.java server/managers/*.java server/models/*.java server/repositories/*.java server/utils/*.java
     ```
   * **On macOS / Linux:**
     ```bash
     javac -d out -cp "lib/*" gui/*.java server/*.java server/managers/*.java server/models/*.java server/repositories/*.java server/utils/*.java
     ```

3. **Run the application:**
   * **On Windows:**
     ```cmd
     java -cp "out;lib/*" server.Main
     ```
   * **On macOS / Linux:**
     ```bash
     java -cp "out:lib/*" server.Main
     ```

---

## 📄 Usage

1. **Register / Login:** Launch the application to open the Login screen. Create a new account via the **Register** button if you are a first-time user.
2. **Manage Finances:** Navigate to the transactions tab in the main dashboard to log new **Income** or **Expense** entries with descriptions, amounts, and dates.
3. **Write Diary Entries:** Switch to the diary section to record daily notes and reflections.
4. **Export Reports:** Click the **Generate Report** button to export your monthly financial summary and diary entries into a formatted PDF saved inside the `reports/` directory.

---

## 🏗️ OOP Concepts Applied

* **Inheritance & Polymorphism:** `Income` and `Expense` classes extend the base `Transaction` class, overriding specific behaviors and calculations.
* **Encapsulation:** Model fields are kept `private` with controlled access through public getters, setters, and manager classes.
* **Abstraction & Separation of Concerns:** Cleanly separated into **GUI** (View), **Managers** (Service/Controller), **Repositories** (Data Access), and **Models** (Entities).