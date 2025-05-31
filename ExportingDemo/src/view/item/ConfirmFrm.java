package view.item;

import dao.ExportBillDAO;
import dao.ItemDAO;
import model.ExportBill;
import model.ExportedItem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ConfirmFrm extends JFrame implements ActionListener {
    private ExportBill bill = new ExportBill();
    private JTable tblExportItems;
    private DefaultTableModel tableModel;
    private JButton btnConfirm;
    private JButton btnBack;
    private JButton btnCancel;

    private static final String[] COLUMNS = {"No", "Item Name", "Quantity", "Unit Price", "Total"};

    public ConfirmFrm(ExportBill bill) {
        this.bill = bill;
        tableModel = new DefaultTableModel(COLUMNS, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblExportItems = new JTable(tableModel);
        tblExportItems.setRowHeight(25);
        tblExportItems.setAutoCreateRowSorter(true);

        btnConfirm = new JButton("Confirm");
        btnBack = new JButton("Back");
        btnCancel = new JButton("Cancel");
        setTitle("Export Confirmation");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Main panel
        JPanel pnMain = new JPanel(new BorderLayout(10, 10));
        pnMain.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Table
        JScrollPane scrollPane = new JScrollPane(tblExportItems);
        pnMain.add(scrollPane, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new BorderLayout());
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));

        rightPanel.add(btnConfirm);
        leftPanel.add(btnBack);
        leftPanel.add(btnCancel);

        btnBack.addActionListener(this);
        btnConfirm.addActionListener(this);
        btnCancel.addActionListener(this);

        buttonPanel.add(leftPanel, BorderLayout.WEST);
        buttonPanel.add(rightPanel, BorderLayout.EAST);
        pnMain.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(pnMain);
        ArrayList<ExportedItem> ei = this.bill.getExportItem();
        updateTable(ei);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if ((e.getSource() instanceof JButton) && (e.getSource()).equals(btnBack))
        {
            this.dispose();
            new SearchExportItemFrm(this.bill).setVisible(true);
        }
        else if ((e.getSource() instanceof JButton) && (e.getSource()).equals(btnCancel))
        {
            this.bill.setExportItem(new ArrayList<>());
            this.dispose();
            new SearchExportItemFrm(this.bill).setVisible(true);
        }
        else if ((e.getSource() instanceof JButton) && (e.getSource()).equals(btnConfirm))
        {
            if (this.bill.getExportItem().isEmpty())
            {
                JOptionPane.showMessageDialog(this, "There are no item in the list, please add at least one", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                ExportBillDAO ebd = new ExportBillDAO();
                if (ebd.addExportBill(this.bill)) {
                    JOptionPane.showMessageDialog(this, "Bill has been successfully created", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "There are errors while creating bill", "Error", JOptionPane.ERROR_MESSAGE);
                }
                this.dispose();
                new ExportBillFrm(this.bill).setVisible(true);
            }
        }
    }

    public void updateTable(ArrayList<ExportedItem> ei) {
        tableModel.setRowCount(0);
        int id = 1;
        for (ExportedItem e : ei) {
            tableModel.addRow(new Object[]{
                    id,
                    e.getI().getItemName(),
                    e.getQuantity(),
                    e.getUnitPrice(),
                    e.getTotalPrice()
            });
            id++;
        }
    }
    public static void main(String[] args) {
    }
}
