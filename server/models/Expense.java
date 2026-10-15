package models;

import utils.TransactionTypeEnum;

public class Expense extends Transaction {
    public Expense (double amount, String date, String msg) {
        super(amount,date,msg);
        super.setType(TransactionTypeEnum.EXPENSE);
    }
}
