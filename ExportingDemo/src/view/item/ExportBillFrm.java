package view.item;

import model.ExportBill;
import model.ExportedItem;
import view.user.StockClerkHomeFrm;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ExportBillFrm extends JFrame implements ActionListener {
    private ExportBill bill = new ExportBill();
    private JTable tblItems;
    private DefaultTableModel tableModel;
    private JButton btnOK;
    private JLabel lblBillID;
    private JLabel lblSubAgentID;
    private JLabel lblBrandName;
    private JLabel lblPhone;
    private JLabel lblAddress;
    private JLabel lblDateIssued;
    private JLabel lblCreatedBy;
    private JLabel lblTotalPrice;

    private static final String[] COLUMNS = {"No", "Name", "Quantity", "Unit Price (thousand VND)", "Total Price (thousand VND)"};

    public ExportBillFrm(ExportBill bill) {
        this.bill = bill;
        // Labels
        lblBillID = new JLabel("Bill ID: ");
        lblSubAgentID = new JLabel("Sub-agent ID: ");
        lblBrandName = new JLabel("Brand name: ");
        lblPhone = new JLabel("Phone number: ");
        lblAddress = new JLabel("Address: ");
        lblDateIssued = new JLabel("Date issued: ");
        lblCreatedBy = new JLabel("Created by: ");
        lblTotalPrice = new JLabel("Total price: ");

        // Initialize table
        tableModel = new DefaultTableModel(COLUMNS, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }
        };
        tblItems = new JTable(tableModel);
        tblItems.setRowHeight(25);

        // Initialize button
        btnOK = new JButton("OK");
        setTitle("Export Bill");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);

        // Main panel
        JPanel pnMain = new JPanel(new BorderLayout(10, 10));
        pnMain.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.BLACK, 1),
                new EmptyBorder(10, 10, 10, 10)));

        // Header
        JPanel headerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(2, 5, 2, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JPanel billIDPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        billIDPanel.add(lblBillID);

        // Add bill ID
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        headerPanel.add(billIDPanel, gbc);

        // Sub-agent details
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        headerPanel.add(lblSubAgentID, gbc);

        gbc.gridy = 2;
        headerPanel.add(lblBrandName, gbc);

        gbc.gridy = 3;
        headerPanel.add(lblPhone, gbc);

        gbc.gridy = 4;
        headerPanel.add(lblAddress, gbc);

        gbc.gridy = 5;
        headerPanel.add(lblDateIssued, gbc);

        gbc.gridy = 6;
        headerPanel.add(lblCreatedBy, gbc);

        // Add header panel to main panel
        pnMain.add(headerPanel, BorderLayout.NORTH);

        // Table panel
        JScrollPane scrollPane = new JScrollPane(tblItems);
        pnMain.add(scrollPane, BorderLayout.CENTER);

        // Footer panel
        JPanel footerPanel = new JPanel(new BorderLayout(5, 5));

        // Total price label
        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        totalPanel.add(lblTotalPrice);
        footerPanel.add(totalPanel, BorderLayout.NORTH);

        // OK button centered
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnOK.setPreferredSize(new Dimension(100, 30));
        buttonPanel.add(btnOK);
        footerPanel.add(buttonPanel, BorderLayout.CENTER);

        btnOK.addActionListener(this);

        pnMain.add(footerPanel, BorderLayout.SOUTH);

        // Set the content pane
        setContentPane(pnMain);
        double totalprice = 0;
        for (ExportedItem e: bill.getExportItem())
        {
            totalprice += (e.getUnitPrice() * e.getQuantity());
        }
        setBillInfo(bill.getID(), bill.getS().getID(), bill.getS().getBrandName(), bill.getS().getPhoneNumber(), bill.getS().getAddress(), bill.getDateIssued(), bill.getU().getFullName(), totalprice);
        setItemTable(bill.getExportItem());
    }

    public void setBillInfo(int billID, int subAgentID, String brandName, String phone, String address, Date dateIssued, String createdBy, double totalprice) {
        lblBillID.setText("Bill ID: " + billID);
        lblSubAgentID.setText("Sub-agent ID: " + subAgentID);
        lblBrandName.setText("Brand name: " + brandName);
        lblPhone.setText("Phone number: " + phone);
        lblAddress.setText("Address: " + address);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        lblDateIssued.setText("Date issued: " + dateFormat.format(dateIssued));

        lblCreatedBy.setText("Created by: " + createdBy);
        lblTotalPrice.setText("Total Price: " + totalprice);
    }

    public void setItemTable(ArrayList<ExportedItem> exportitems) {
        tableModel.setRowCount(0);
        int id = 1;
        for (ExportedItem item : exportitems) {
            tableModel.addRow(new Object[]{
                    id,
                    item.getI().getItemName(),
                    item.getQuantity(),
                    item.getUnitPrice(),
                    item.getTotalPrice()
            });
            id++;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       if ((e.getSource() instanceof JButton) && (e.getSource()).equals(btnOK))
        {
            this.dispose();
            new StockClerkHomeFrm(bill.getU()).setVisible(true);
        }
    }

    public static void main(String[] args) {

    }
}