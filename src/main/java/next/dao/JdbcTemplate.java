package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
	
	List query(String query) throws SQLException {
		ArrayList<Object> list = new ArrayList<Object>();
    	Connection con = null;
    	PreparedStatement pstmt = null;
    	ResultSet rs = null;
    	
    	try {
    		con = ConnectionManager.getConnection();
    		pstmt = con.prepareStatement(query);
    		setValues(pstmt);
    		rs = pstmt.executeQuery();
    		
    		while(rs.next()) {
    			list.add(mapRow(rs));
    		}
    		
    		return list;
    	} finally {
    		if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (con != null) {
                con.close();
            }
    	}
	}
	
	Object queryForObject(String query) {
		return null;
	}
	abstract void setValues(PreparedStatement pstmt) throws SQLException;
	abstract Object mapRow(ResultSet rs) throws SQLException;
}
