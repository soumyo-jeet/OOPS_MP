package managers;

import java.util.*;
import models.*;
import repositories.DiaryRepository;
import utils.TransactionTypeEnum;

public class DiaryManager {
    User user;
    DiaryRepository diaryRepository;

    public DiaryManager (User user, DiaryRepository diaryRepository) {
        this.user = user;
        this.diaryRepository = diaryRepository;
    }

    public void addNewDiary (int mon, int year, double expIncome, double budget) {
        if(year <= 2000 || year >= 2999 || mon < 1 || mon > 12 || budget <= 0 || expIncome <= 0) {
            throw new RuntimeException("Invalid arguments.Please try again.");
        }

        Diary existedDiary = diaryRepository.findDiaryByUserMonYr(user, mon, year);
        if(existedDiary != null) {
            throw new RuntimeException("Diary for this month is already present, please open it to write new transactions.");
        }
        
        Diary d = new Diary(mon, year, expIncome, budget);
        int res = diaryRepository.createNewDiary(user, d);
        
        if(res == 1) System.out.println("Diary created successfully.");
        else throw new RuntimeException("Diary creation failed.");
    }

    public Diary openDiary (int mon, int year) {
        Diary existedDiary = diaryRepository.findDiaryByUserMonYr(user, mon, year);

        if(existedDiary != null) {
            System.out.println("Diary opened: ");
            System.out.println(existedDiary);
            return existedDiary;
        }

        throw new RuntimeException("Diary not found.Please create new one");
    }

    public List<Diary> getAll() {
        return diaryRepository.findAllByUser(user);
    }

    public void recordTransaction (Diary d, Transaction tx) {
        if(tx.getType() == TransactionTypeEnum.EXPENSE) {
            d.setBudgetLeft(d.getBudgetLeft() - tx.getAmount());
            d.setBalance(d.getBalance() - tx.getAmount());
        } 
        else d.setBalance(d.getBalance() + tx.getAmount());

        diaryRepository.updateDiary(d);

        System.out.println("New transaction noted on\n" + d);
    }
}