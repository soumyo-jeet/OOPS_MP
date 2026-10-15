package models;

import utils.TransactionTypeEnum;

public abstract class Transaction {
    
    private int id;
    private double amount;
    private String date;
    private String msg;
    private TransactionTypeEnum type;


    public Transaction(double amount, String date, String msg) {
        this.amount = amount;
        this.date = date;
        this.msg = msg;
    }


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public TransactionTypeEnum getType() {
        return type;
    }
    public void setType(TransactionTypeEnum type) {
        this.type = type;
    }


    @Override
    public String toString () {
        return """
                Transaction:
                id: %d,
                amount: %f,
                date: %s,
                type: %s,
                msg: %s,
                """.formatted(this.id, this.amount, this.date, this.type, this.msg);
    }
}