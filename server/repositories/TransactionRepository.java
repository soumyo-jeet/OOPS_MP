package repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import models.*;
import utils.TransactionTypeEnum;

public class TransactionRepository {
    public int createNewTxn(Diary diary, Transaction tx) {
        String sql = """
                    INSERT INTO transactions ('diary_id', 'amount', 'date', 'message', 'type')
                    VALUES (?, ?, ?, ?, ?);
                    """;
        int ret = 0;

        try (
            Connection conn = Database.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sql)
        ) {
            preparedStatement.setInt(1, diary.getId());
            preparedStatement.setDouble(2, tx.getAmount());
            preparedStatement.setString(3, tx.getDate());
            preparedStatement.setString(4, tx.getMsg());
            preparedStatement.setString(5, tx.getType().name());
            
            ret = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public List<Transaction> findAllByDiary (Diary diary) {
        String sql = """
                    SELECT * FROM transactions WHERE diary_id = ?
                    """;
        List<Transaction> txs = new ArrayList<>();

        try (
            Connection conn = Database.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sql)
        ) {
            preparedStatement.setInt(1, diary.getId());
            
            ResultSet res = preparedStatement.executeQuery();

            while(res.next()) {
                Transaction tx = new Transaction(0, null, null);
    
                tx.setAmount(res.getDouble("amount"));
                tx.setDate(res.getString("date"));
                tx.setMsg(res.getString("message"));
                tx.setType(TransactionTypeEnum.valueOf(res.getString("type")));
                tx.setId(res.getInt("id"));

                txs.add(tx);
            }
           
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return txs;
    }
}
