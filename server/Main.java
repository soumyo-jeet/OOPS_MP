import java.util.List;
import managers.DiaryManager;
import managers.TransactionManager;
import managers.UserManager;
import models.Diary;
import models.Transaction;
import models.User;
import repositories.DiaryRepository;
import repositories.TransactionRepository;
import repositories.UserRepository;
public class Main {
    public static void main(String[] args) {
        // User user = new User("soumyo", "pass");
        // System.out.println(user);

        // DiaryManager dm = new DiaryManager(user);
        // dm.addNewDiary(2, 2026, 10000, 5000);
        // System.out.println(user);

        // Diary openedDiary = dm.openDiary(2, 2026);
        // System.out.println(openedDiary);

        // TransactionManager tm = new TransactionManager(dm);
        // tm.handleExp(openedDiary, 10, "1/2/2026", "");
        // try {
        //     Database.createTables();
        // } catch (Exception e) {
        //     System.out.println(e.getMessage());
        // }



        UserManager userManager = new UserManager(new UserRepository());
        // userManager.addNewUser("soumyo", "jeet");

        User authenticatedUser = userManager.authenticateUser(new User("soumyo", "jeet"));
        
        DiaryManager diaryManager = new DiaryManager(authenticatedUser, new DiaryRepository());
        // diaryManager.addNewDiary(3, 2026, 10000, 5000);
        Diary diary = diaryManager.openDiary(3, 2026);

        List<Diary> diaries = diaryManager.getAll();
        for(Diary d: diaries) {
            System.out.println(d);
        }

        TransactionManager transactionManager = new TransactionManager(diaryManager, new TransactionRepository());
        transactionManager.handleIncm(diary, 1000, "4", null);
        List<Transaction> txs = transactionManager.listAllTxs(diary);
        for(Transaction t: txs) {
            System.out.println(t);
        }
    }
}



// CREATE user
// AUTHENTICATE user
// SET AUTHENTICATED user AS diary manager's dependency
// ADD a diary 
// OPEN a diary
// SET OPENED diary AS txn manager's dependency
// STORE txns