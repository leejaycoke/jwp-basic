package next.dao;

import core.jdbc.ConnectionManager;
import next.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    public void insert(User user) throws SQLException {
        NiceJdbcTemplate template = new NiceJdbcTemplate();

        String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";
        template.insert(sql, pstmt -> {
            pstmt.setString(1, user.getUserId());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getName());
            pstmt.setString(4, user.getEmail());
        });
    }

    public void update(User user) throws SQLException {
        NiceJdbcTemplate template = new NiceJdbcTemplate();

        String sql = "UPDATE USERS SET password=?, name=?, email=?";
        template.update(sql, pstmt -> {
            pstmt.setString(1, user.getPassword());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getEmail());
        });
    }

    public List<User> findAll() throws SQLException {
        NiceJdbcTemplate template = new NiceJdbcTemplate();

        return template.findAll("SELECT * FROM USERS", rs -> {
            List<User> users = new ArrayList<>();
            while (rs.next()) {
                String userId = rs.getString("userId");
                String password = rs.getString("password");
                String name = rs.getString("name");
                String email = rs.getString("email");
                users.add(new User(userId, password, name, email));
            }
            return users;
        });

    }

    public User findByUserId(String userId) throws SQLException {
        try (Connection con = ConnectionManager.getConnection()) {
            String sql = "SELECT userId, password, name, email FROM USERS WHERE userid=?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                User user = null;
                if (rs.next()) {
                    user = new User(rs.getString("userId"),
                            rs.getString("password"),
                            rs.getString("name"),
                            rs.getString("email"));
                }

                return user;
            }
        }
    }

}
