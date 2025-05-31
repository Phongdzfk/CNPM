package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.User;

public class UserDAO extends DAO{
	
	public UserDAO() {
		super();
	}
	
	public boolean checkLogin(User user) {
		boolean result = false;
		String sql = "SELECT ID, fullName, Role, phoneNumber, emailAddress FROM tblUser WHERE username = ? AND password = ?";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				user.setID(rs.getInt("ID"));
				user.setFullName(rs.getString("fullName"));
				user.setEmailAddress(rs.getString("emailAddress"));
				user.setRole(rs.getString("Role"));
				user.setPhoneNumber(rs.getString("phoneNumber"));
				result = true;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
