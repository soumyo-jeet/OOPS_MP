import managers.DiaryManager;
import managers.TransactionManager;
import models.Diary;
import models.User;

public class Main {
    public static void main(String[] args) {
        User user = new User("soumyo", "pass");
        System.out.println(user);

        DiaryManager dm = new DiaryManager(user);
        dm.addNewDiary(2, 2026, 10000, 5000);
        System.out.println(user);

        Diary openedDiary = dm.openDiary(2, 2026);
        System.out.println(openedDiary);

        TransactionManager tm = new TransactionManager(dm);
        tm.handleExp(openedDiary, 10, "1/2/2026", "");
    }
}
