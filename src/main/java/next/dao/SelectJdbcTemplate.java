package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.jdbc.ConnectionManager;
import next.model.User;

public abstract class SelectJdbcTemplate {
	List query(String query) throws SQLException {
		ArrayList<User> list = new ArrayList<User>();
    	Connection con = null;
    	PreparedStatement pstmt = null;
    	ResultSet rs = null;
    	
    	try {
    		con = ConnectionManager.getConnection();
    		pstmt = con.prepareStatement(query);
    		setValue(pstmt);
    		rs = pstmt.executeQuery();
    		
    		while(rs.next()) {
    			list.add(new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"), rs.getString("email")));
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
	
	Object queryForObject(String query) throws SQLException {
		return null;
	}
	
	abstract void setValue(PreparedStatement pstmt);
	abstract Object mapRow(ResultSet rs);
}
