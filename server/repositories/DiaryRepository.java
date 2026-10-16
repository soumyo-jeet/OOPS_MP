package repositories;

import java.sql.*;
import java.util.*;
import models.*;

public class DiaryRepository {
    public int createNewDiary(User user, Diary diary) {
        String sql = """
                    INSERT INTO diaries ('user_id', 'month', 'year', 'expected_income', 'budget', 'budget_left', 'balance')
                    VALUES (?, ?, ?, ?, ?, ?, ?);
                    """;
        int ret = 0;

        try (
            Connection conn = Database.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sql)
        ) {
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setInt(2, diary.getMon());
            preparedStatement.setInt(3, diary.getYear());
            preparedStatement.setDouble(4, diary.getExpIncome());
            preparedStatement.setDouble(5, diary.getBudget());
            preparedStatement.setDouble(6, diary.getBudgetLeft());
            preparedStatement.setDouble(7, diary.getBalance());

            ret = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public int updateDiary(Diary diary) {
        String sql = """
                    UPDATE diaries
                    SET budget_left = ?, balance = ?, budget = ?
                    WHERE id = ?;
                    """;
        int ret = 0;

        try (
            Connection conn = Database.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sql)
        ) {
            preparedStatement.setDouble(1, diary.getBudgetLeft());
            preparedStatement.setDouble(2, diary.getBalance());
            preparedStatement.setDouble(3, diary.getBudget());
            preparedStatement.setInt(4, diary.getId());


            ret = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }


    public Diary findDiaryByUserMonYr (User user, int mon, int year) {
        String sql = """
                    SELECT * FROM diaries WHERE user_id = ? AND month = ? AND year = ?
                    """;
        Diary diary = null;

        try (
            Connection conn = Database.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sql)
        ) {
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setInt(2, mon);
            preparedStatement.setInt(3, year);


            ResultSet res = preparedStatement.executeQuery();

            if(!res.next()) return null;
            

            diary = new Diary(0, 0, 0, 0);

            diary.setId(res.getInt("id"));
            diary.setMon(res.getInt("month"));
            diary.setYear(res.getInt("year"));
            diary.setExpIncome(res.getDouble("expected_income"));
            diary.setBudget(res.getDouble("budget"));
            diary.setBudgetLeft(res.getDouble("budget_left"));
            diary.setBalance(res.getDouble("balance"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return diary;
    }


    public List<Diary> findAllByUser (User user) {
        String sql = """
                    SELECT * FROM diaries WHERE user_id = ?
                    """;
        List<Diary> diaries = new ArrayList<>();

        try (
            Connection conn = Database.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sql)
        ) {
            preparedStatement.setInt(1, user.getId());
            
            ResultSet res = preparedStatement.executeQuery();

            while(res.next()) {
                Diary diary = new Diary(0, 0, 0, 0);
    
                diary.setId(res.getInt("id"));
                diary.setMon(res.getInt("month"));
                diary.setYear(res.getInt("year"));
                diary.setExpIncome(res.getDouble("expected_income"));
                diary.setBudget(res.getDouble("budget"));
                diary.setBudgetLeft(res.getDouble("budget_left"));
                diary.setBalance(res.getDouble("balance"));

                diaries.add(diary);
            }
           
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return diaries;
    }
}
