package next.dao;

import core.jdbc.ConnectionManager;
import next.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class NiceJdbcTemplate {

    public void insert(String sql, QuerySetter querySetter) throws SQLException {
        try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            if (querySetter != null) {
                querySetter.setValues(pstmt);
            }

            pstmt.executeUpdate();
        }

    }

    public void update(String sql, QuerySetter querySetter) throws SQLException {
        insert(sql, querySetter);
    }

    public List<User> findAll(String sql, RowMapper rowMapper) throws SQLException {
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            try (ResultSet rs = pstmt.executeQuery()) {
                return rowMapper.map(rs);
            }
        }
    }

}
// PAGERDUTY
// OPSGENIE

