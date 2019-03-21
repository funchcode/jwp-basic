package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import core.jdbc.ConnectionManager;
import next.model.User;

public abstract class JdbcTemplate {
	void update(String query) throws SQLException {
		Connection con = null;
        PreparedStatement pstmt = null;
        try {
        	con = ConnectionManager.getConnection();
        	pstmt = con.prepareStatement(query);
        	setValues(pstmt);
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
	
	abstract void setValues(PreparedStatement pstmt) throws SQLException;
}
