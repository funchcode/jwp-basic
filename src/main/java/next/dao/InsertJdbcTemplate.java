package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import core.jdbc.ConnectionManager;
import next.model.User;

public abstract class InsertJdbcTemplate {
	void insert(User user) throws SQLException {
		Connection con = null;
        PreparedStatement pstmt = null;
        try {
        	con = ConnectionManager.getConnection();
        	pstmt = con.prepareStatement(createQueryForInsert());
        	setValuesForInsert(user, pstmt);
        	pstmt.executeUpdate();
        } finally {
        	if (pstmt != null) {
                pstmt.close();
            }

            if (con != null) {
                con.close();
            }
        }
	}
	
	abstract void setValuesForInsert(User user, PreparedStatement pstmt) throws SQLException;
	abstract String createQueryForInsert();
}
