package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import model.SubAgent;

public class SubAgentDAO extends DAO {

    public SubAgentDAO() {
        super();
    }

    public ArrayList<SubAgent> searchSubAgentByName(String name) {
        ArrayList<SubAgent> result = new ArrayList<SubAgent>();
        String sql = "SELECT * FROM tblSubAgent WHERE brandName LIKE ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%" + name + "%");
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                SubAgent subAgent = new SubAgent();
                subAgent.setID(rs.getInt("ID"));
                subAgent.setBrandName(rs.getString("brandName"));
                subAgent.setAddress(rs.getString("Address"));
                subAgent.setPhoneNumber(rs.getString("phoneNumber"));
                result.add(subAgent);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean addSubAgent(SubAgent sa) {
        boolean result = false;
        String sql = "INSERT INTO tblSubAgent(brandName, Address, phoneNumber) VALUES(?, ?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, sa.getBrandName());
            ps.setString(2, sa.getAddress());
            ps.setString(3, sa.getPhoneNumber());
            if(ps.executeUpdate() > 0) {
                result = true;
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
