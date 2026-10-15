package models;

import utils.TransactionTypeEnum;

public class Income extends Transaction {
    public Income (double amount, String date, String msg) {
        super(amount,date,msg);
        super.setType(TransactionTypeEnum.INCOME);
    }
}
