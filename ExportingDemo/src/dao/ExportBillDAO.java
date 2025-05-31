package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import model.ExportBill;
import model.ExportedItem;

public class ExportBillDAO extends DAO {

    public ExportBillDAO() {
        super();
    }

    public boolean addExportBill(ExportBill eb) {
        boolean result = false;
        try {
            con.setAutoCommit(false);

            // Insert the export bill
            String billSql = "INSERT INTO tblExportBill(dateIssued, UserID, SubAgentID) VALUES(?, ?, ?)";
            PreparedStatement billPs = con.prepareStatement(billSql, Statement.RETURN_GENERATED_KEYS);
            billPs.setDate(1, new java.sql.Date(eb.getDateIssued().getTime()));
            billPs.setInt(2, eb.getU().getID());
            billPs.setInt(3, eb.getS().getID());

            int billRows = billPs.executeUpdate();

            if (billRows > 0) {
                ResultSet generatedKeys = billPs.getGeneratedKeys();
                int billID = 0;
                if (generatedKeys.next()) {
                    billID = generatedKeys.getInt(1);
                }
                eb.setID(billID);

                // Insert exported items
                String itemSql = "INSERT INTO tblExportedItem(ItemID, ExportBillID, Quantity, unitPrice) VALUES(?, ?, ?, ?)";
                PreparedStatement itemPs = con.prepareStatement(itemSql);

                for (ExportedItem item : eb.getExportItem()) {
                    itemPs.setInt(1, item.getI().getID());
                    itemPs.setInt(2, billID);
                    itemPs.setInt(3, item.getQuantity());
                    itemPs.setDouble(4, item.getUnitPrice());
                    itemPs.addBatch();
                }

                int[] itemResults = itemPs.executeBatch();

                // Check
                boolean allItemsInserted = true;
                for (int i : itemResults) {
                    if (i <= 0) {
                        allItemsInserted = false;
                        break;
                    }
                }

                if (allItemsInserted) {
                    con.commit();//cmt this for test
                    result = true;
                } else {
                    con.rollback();
                }
            } else {
                con.rollback();
            }

            // Restore auto-commit
            con.setAutoCommit(true); //Cmt this for test

        } catch(Exception e) {
            try {
                con.rollback();
                con.setAutoCommit(true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
        return result;
    }
}
