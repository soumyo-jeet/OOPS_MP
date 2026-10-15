package managers;

import models.*;

public class TransactionManager {
    DiaryManager diaryManager;

    public TransactionManager (DiaryManager diaryManager) {
        this.diaryManager = diaryManager;
    }


    public void handleExp (Diary d, double amt, String date, String msg) {
        Expense ex = new Expense(amt, date, msg);
        System.out.println(ex);
        diaryManager.recordTransaction(d, ex);
    }

    public void handleIncm (Diary d, double amt, String date, String msg) {
        Income inc = new Income(amt, date, msg);
        System.out.println(inc);
        diaryManager.recordTransaction(d, inc);
    }
}
