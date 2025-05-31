package view.item;

import model.ExportBill;
import model.ExportedItem;
import model.Item;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AddExportItemFrm extends JFrame implements ActionListener {
    private ExportBill bill = new ExportBill();
    private Item item = new Item();
    private JTextField txtQuantity;
    private JTextField txtUnitPrice;
    private JButton btnSubmit;
    private JButton btnCancel;

    public AddExportItemFrm(ExportBill bill, Item item) {
        this.bill = bill;
        this.item = item;
        setTitle("Add Export Item");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null);

        // Main panel
        JPanel pnMain = new JPanel(new BorderLayout());
        pnMain.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 8, 0, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Quantity label + field
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Quantity:"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        txtQuantity = new JTextField(20);
        formPanel.add(txtQuantity, gbc);

        // Unit price label + field
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(15, 8, 0, 8);
        formPanel.add(new JLabel("Unit price (thousand VND):"), gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 8, 0, 8);
        txtUnitPrice = new JTextField(20);
        txtUnitPrice.setText(Double.toString(this.item.getUnitPrice()));
        formPanel.add(txtUnitPrice, gbc);

        // Button panel
        JPanel buttonPanel = new JPanel(new BorderLayout());
        btnSubmit = new JButton("Submit");
        btnCancel = new JButton("Cancel");

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.add(btnSubmit);

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.add(btnCancel);

        buttonPanel.add(leftPanel, BorderLayout.WEST);
        buttonPanel.add(rightPanel, BorderLayout.EAST);

        // Add to main panel
        pnMain.add(formPanel, BorderLayout.CENTER);
        pnMain.add(buttonPanel, BorderLayout.SOUTH);

        // Padding
        JPanel paddingPanel = new JPanel(new BorderLayout());
        paddingPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        paddingPanel.add(pnMain, BorderLayout.CENTER);
        setContentPane(paddingPanel);

        // Action
        btnSubmit.addActionListener(this);
        btnCancel.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ((e.getSource() instanceof JButton) && (e.getSource()).equals(btnCancel)) {
            this.dispose();
        } else if ((e.getSource() instanceof JButton) && (e.getSource()).equals(btnSubmit)) {
            if (txtQuantity.getText().trim().isEmpty() || txtUnitPrice.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields must be filled", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                int inputQuan = Integer.parseInt(txtQuantity.getText().trim());
                double inputUP = Double.parseDouble(txtUnitPrice.getText().trim());

                if (inputQuan <= 0) {
                    JOptionPane.showMessageDialog(this, "Quantity must be greater than 0", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (inputUP <= 0) {
                    JOptionPane.showMessageDialog(this, "Unit Price must be greater than 0", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                ArrayList<ExportedItem> tmp = this.bill.getExportItem();
                boolean merged = false;
                int newQuantity = inputQuan;
                int tmpQuan = this.item.getStockQuantity() - newQuantity;
                if (tmpQuan < 0)
                {
                    JOptionPane.showMessageDialog(this, "Total quantity exceeds stock!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                for (ExportedItem ei : tmp) {
                    if (ei.getI().getID() == this.item.getID()) {
                        tmpQuan = tmpQuan - ei.getQuantity();
                        if (tmpQuan < 0) {
                            JOptionPane.showMessageDialog(this, "Total quantity exceeds stock!", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                }
                for (ExportedItem ei : tmp) {
                    if (ei.getUnitPrice() == inputUP && ei.getI().getID() == this.item.getID()) {
                        newQuantity += ei.getQuantity();
                        ei.setQuantity(newQuantity);
                        ei.setTotalPrice(newQuantity * ei.getUnitPrice());
                        merged = true;
                        break;
                    }
                }
                if (!merged) {
                    ExportedItem ei = new ExportedItem(1, inputQuan, inputUP, this.item);
                    tmp.add(ei);
                }
                this.bill.setExportItem(tmp);
                JOptionPane.showMessageDialog(this, "Item has been added for exporting", "Success", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
            } catch (NumberFormatException ne) {
                JOptionPane.showMessageDialog(this, "Quantity and Unit Price fields must be numbers, please try again", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
    }
}
