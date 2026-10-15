package models;
import java.util.*;

public class Diary {
    private int id;
    private int mon;
    private int year;
    private double expIncome;
    private double budget;
    private double budgetLeft;
    private double balance;
    private List<Transaction> txns;

    public Diary(int mon, int year, double expIncome, double budget) {
        this.mon = mon;
        this.year = year;
        this.expIncome = expIncome;
        this.budget = budget;
        this.balance = 0;
        this.budgetLeft = budget;
        txns = new ArrayList<>();
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }


    public void setMon (int m) {
        mon = m;
    }

    public void setYear(int y) {
        year = y;
    }

    public int getMon() {
        return mon;
    }

    public int getYear() {
        return year;
    }

    public double getExpIncome() {
        return expIncome;
    }

    public void setExpIncome(double expIncome) {
        this.expIncome = expIncome;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public List<Transaction> getTxns() {
        return txns;
    }

    public void addTxns(Transaction txn) {
        this.txns.add(txn);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

   

    public double getBudgetLeft() {
        return budgetLeft;
    }

    public void setBudgetLeft(double budgetLeft) {
        this.budgetLeft = budgetLeft;
    }

    @Override
    public String toString () {
        return """
                Diary:
                id: %d,
                mon: %d,
                year: %d,
                expIncome: %f,
                budget: %f,
                balance: %f,
                budget left: %f
                """.formatted(this.id, this.mon, this.year, this.expIncome, this.budget, this.balance, this.budgetLeft);
    }
    
}
