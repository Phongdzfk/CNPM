package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import model.Item;

public class ItemDAO extends DAO {

    public ItemDAO() {
        super();
    }

    public ArrayList<Item> searchExportItem(String name) {
        ArrayList<Item> result = new ArrayList<>();
        String sql = "SELECT i.ID, i.itemName, i.Description, i.unitPrice, "
                + "(COALESCE(im.total_import, 0) - COALESCE(ex.total_export, 0)) AS stockQuantity "
                + "FROM tblItem i "
                + "LEFT JOIN (SELECT ItemID, SUM(Quantity) AS total_import FROM tblImportedItem GROUP BY ItemID) im "
                + "ON i.ID = im.ItemID "
                + "LEFT JOIN (SELECT ItemID, SUM(Quantity) AS total_export FROM tblExportedItem GROUP BY ItemID) ex "
                + "ON i.ID = ex.ItemID "
                + "WHERE i.itemName LIKE ?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%" + name + "%");
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Item item = new Item();
                item.setID(rs.getInt("ID"));
                item.setItemName(rs.getString("itemName"));
                item.setDescription(rs.getString("Description"));
                item.setUnitPrice(rs.getDouble("unitPrice"));
                item.setStockQuantity(rs.getInt("stockQuantity"));
                result.add(item);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
