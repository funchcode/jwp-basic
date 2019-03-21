package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.jdbc.ConnectionManager;
import next.model.User;

public class UserDao {
	
	void setValuesForInsert(User user, PreparedStatement pstmt) throws SQLException {
		pstmt.setString(1, user.getUserId());
        pstmt.setString(2, user.getPassword());
        pstmt.setString(3, user.getName());
        pstmt.setString(4, user.getEmail());
	}
	
	void setValuesForUpdate(User user, PreparedStatement pstmt) throws SQLException {
		pstmt.setString(4, user.getUserId());
		pstmt.setString(1, user.getPassword());
		pstmt.setString(2, user.getName());
		pstmt.setString(3, user.getEmail());
	}
	
	String createQueryForInsert() {
		return "INSERT INTO USERS VALUES (?,?,?,?)";
	}
	
	String createQueryForUpdate() {
		return "UPDATE USERS SET password = ?, name = ?, email = ? WHERE userid = ?";
	}
	
    public void insert(User user) throws SQLException {
    	String query = "INSERT INTO USERS VALUES (?,?,?,?)";
    	JdbcTemplate insertJdbc = new JdbcTemplate() {
			@Override
			void setValues(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, user.getUserId());
		        pstmt.setString(2, user.getPassword());
		        pstmt.setString(3, user.getName());
		        pstmt.setString(4, user.getEmail());
			}
    	};
    	insertJdbc.update(query);
    }

    public void update(User user) throws SQLException {
        UpdateJdbcTemplate.update(user, new UserDao());
    }

    public List<User> findAll() throws SQLException {
        // TODO 구현 필요함.
    	ArrayList<User> list = new ArrayList<User>();
    	Connection con = null;
    	PreparedStatement pstmt = null;
    	ResultSet rs = null;
    	
    	try {
    		con = ConnectionManager.getConnection();
    		String sql = "SELECT userId, password, name, email FROM USERS";
    		pstmt = con.prepareStatement(sql);
    		
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

    public User findByUserId(String userId) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            String sql = "SELECT userId, password, name, email FROM USERS WHERE userid=?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, userId);

            rs = pstmt.executeQuery();

            User user = null;
            if (rs.next()) {
                user = new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
                        rs.getString("email"));
            }

            return user;
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
}
