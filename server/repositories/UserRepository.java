package repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import models.User;

public class UserRepository {
    public int registerUser (User user) {
        String sql = """
                    INSERT INTO users('username', 'password')
                    VALUES (?, ?)
                    """;
        int ret = 0;

        try (
            Connection conn = Database.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPass());

            int res = preparedStatement.executeUpdate();
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public User findUserByUsername (String username) {
        String sql = """
                    SELECT * FROM users WHERE username = ?
                    """;
        User user = null;

        try (
            Connection conn = Database.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, username);

            ResultSet res = preparedStatement.executeQuery();

            if(!res.next()) return null;

            user = new User(null, null);

            user.setId(res.getInt("id"));
            user.setUsername(res.getString("username"));
            user.setPass(res.getString("password"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}
