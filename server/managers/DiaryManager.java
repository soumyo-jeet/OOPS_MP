package managers;

import models.*;
import utils.TransactionTypeEnum;

public class DiaryManager {
    User user;

    public DiaryManager (User user) {
        this.user = user;
    }

    public void addNewDiary (int mon, int year, double expIncome, double budget) {
        Diary d = new Diary(mon, year, expIncome, budget);
        user.addDiaries(d);
    }

    public Diary openDiary (int mon, int year) {
        for(Diary d: user.getDiaries()) {
            if(d.getMon() == mon && d.getYear() == year) return d;
        }
        return null;
    }

    public void recordTransaction (Diary d, Transaction tx) {
        d.addTxns(tx);
        if(tx.getType() == TransactionTypeEnum.EXPENSE) {
            d.setBudgetLeft(d.getBudgetLeft() - tx.getAmount());
            d.setBalance(d.getBalance() - tx.getAmount());
        } 
        else d.setBalance(d.getBalance() + tx.getAmount());

        System.out.println("New transaction noted on\n" + d);
    }
}