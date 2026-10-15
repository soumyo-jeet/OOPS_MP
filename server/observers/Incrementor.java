package observers;
import models.Income;

public class Incrementor implements TransactionObserver {
    Income incomeTxn;

    @Override
    public void updateDiary() {
        System.out.println("Received TXN:\n" + incomeTxn);
    }
    
}
