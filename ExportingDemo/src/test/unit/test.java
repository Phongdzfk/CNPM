package test.unit;

import dao.*;
import model.*;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class test {
    ItemDAO itemDAO = new ItemDAO();
    UserDAO userDAO = new UserDAO();
    SubAgentDAO subAgentDAO = new SubAgentDAO();
    ExportBillDAO exportBillDAO = new ExportBillDAO();

    @Test
    public void testExportProductWithMultipleItems() {
        Connection con = DAO.con;
        boolean initialAutoCommit = true;
        try {
            initialAutoCommit = con.getAutoCommit();
            con.setAutoCommit(false);

            User user = new User();
            user.setID(1);
            user.setUsername("A");
            user.setPassword("123");
            Assert.assertTrue("Login fail",userDAO.checkLogin(user));

            ArrayList<SubAgent> subAgents = subAgentDAO.searchSubAgentByName("LaptopVN");
            Assert.assertNotNull("Sub-agent list must not be null", subAgents);
            Assert.assertEquals("Must have 3 sub-agents", 3, subAgents.size());

            ArrayList<Item> nitroItems = itemDAO.searchExportItem("Nitro 5");
            Assert.assertEquals("Must have 2 items after search", 2, nitroItems.size());

            ArrayList<Item> mouseItems = itemDAO.searchExportItem("Gaming mouse");
            Assert.assertEquals("Must have 2 items after search", 2, mouseItems.size());

            ExportBill bill = new ExportBill();
            bill.setU(user);
            bill.setS(subAgents.get(0));
            bill.setDateIssued(new SimpleDateFormat("dd/MM/yyyy").parse("06/05/2025"));

            ExportedItem ei1 = new ExportedItem();
            ei1.setI(nitroItems.get(0));
            ei1.setQuantity(10);
            ei1.setUnitPrice(20000);

            ExportedItem ei2 = new ExportedItem();
            ei2.setI(mouseItems.get(1));
            ei2.setQuantity(5);
            ei2.setUnitPrice(4000);

            ArrayList<ExportedItem> items = new ArrayList<>();
            items.add(ei1);
            items.add(ei2);
            bill.setExportItem(items);

            boolean saved = exportBillDAO.addExportBill(bill);
            Assert.assertTrue("Fail saving bill", saved);

        } catch (Exception e) {
            Assert.fail("Exception: " + e.getMessage());
        } finally {
            try {
                con.rollback();
                con.setAutoCommit(initialAutoCommit);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

}
