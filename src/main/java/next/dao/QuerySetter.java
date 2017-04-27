package next.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface QuerySetter {

    void setValues(PreparedStatement pstmt) throws SQLException;
}
