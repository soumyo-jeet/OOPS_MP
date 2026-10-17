package managers;

import java.util.List;
import models.*;
import repositories.TransactionRepository;

public class TransactionManager {
    DiaryManager diaryManager;
    TransactionRepository transactionRepository;

    public TransactionManager (DiaryManager diaryManager, TransactionRepository transactionRepository) {
        this.diaryManager = diaryManager;
        this.transactionRepository = transactionRepository;
    }


    public void handleExp (Diary d, double amt, String date, String msg) {
        if(amt <= 0) {
            throw new RuntimeException("Invalid amount.");
        }

        Expense ex = new Expense(amt, date, msg);
        int res = transactionRepository.createNewTxn(d, ex);
        if(res == 0) {
            throw new RuntimeException("Transaction recording failed.");
        }
        System.out.println(ex);
        diaryManager.recordTransaction(d, ex);
    }

    public void handleIncm (Diary d, double amt, String date, String msg) {
        if(amt <= 0) {
            throw new RuntimeException("Invalid amount.");
        }
        
        Income inc = new Income(amt, date, msg);
        int res = transactionRepository.createNewTxn(d, inc);
        if(res == 0) {
            throw new RuntimeException("Transaction recording failed.");
        }
        System.out.println(inc);
        diaryManager.recordTransaction(d, inc);
    }

    public List<Transaction> listAllTxs (Diary d) {
        return transactionRepository.findAllByDiary(d);
    }
}
