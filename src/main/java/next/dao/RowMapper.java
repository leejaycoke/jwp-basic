package next.dao;

import next.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface RowMapper {

    List<User> map(ResultSet rs) throws SQLException;
}
