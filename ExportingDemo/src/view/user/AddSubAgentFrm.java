package view.user;

import dao.SubAgentDAO;
import model.ExportBill;
import model.SubAgent;
import model.User;
import view.item.SearchExportItemFrm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class AddSubAgentFrm extends JFrame implements ActionListener {
    private User currentuser;
    private JTextField txtBrandName;
    private JTextField txtAddress;
    private JTextField txtPhone;
    private JButton btnAdd;
    private JButton btnBack;

    public AddSubAgentFrm(User u) {
        this.currentuser = u;
        txtBrandName = new JTextField(25);
        txtAddress = new JTextField(25);
        txtPhone = new JTextField(15);
        btnAdd = new JButton("Add Sub-agent");
        btnBack = new JButton("Back");
        setLayout(new BorderLayout(10, 10));
        setTitle("Add New Sub-agent");

        // Main form panel
        JPanel pnMain = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Brand Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        pnMain.add(new JLabel("Brand Name:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        pnMain.add(txtBrandName, gbc);

        // Address
        gbc.gridx = 0;
        gbc.gridy = 1;
        pnMain.add(new JLabel("Address:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        pnMain.add(txtAddress, gbc);

        // Phone
        gbc.gridx = 0;
        gbc.gridy = 2;
        pnMain.add(new JLabel("Phone:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        pnMain.add(txtPhone, gbc);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        buttonPanel.add(btnBack);
        buttonPanel.add(btnAdd);

        add(pnMain, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        btnAdd.addActionListener(this);
        btnBack.addActionListener(this);

        setSize(500, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if ((e.getSource() instanceof JButton) && ( e.getSource()).equals(btnBack))
        {
            this.dispose();
            new SearchSubAgentFrm(currentuser).setVisible(true);
        }
        else if ((e.getSource() instanceof JButton) && (e.getSource()).equals(btnAdd))
        {
            if (txtBrandName.getText().isEmpty() || txtAddress.getText().isEmpty() || txtPhone.getText().isEmpty())
            {
                JOptionPane.showMessageDialog(this, "All field must be filled", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else {
                SubAgentDAO sad = new SubAgentDAO();
                SubAgent sa = new SubAgent(10, txtBrandName.getText().trim(), txtAddress.getText().trim(), txtPhone.getText().trim());
                if (sad.addSubAgent(sa)) {
                    JOptionPane.showMessageDialog(this, "Sub agent has been added to the database!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    this.dispose();
                    ExportBill bill = new ExportBill();
                    bill.setU(currentuser);
                    bill.setS(sa);
                    bill.setDateIssued(new Date());
                    new SearchExportItemFrm(bill).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Something wrong happens", "Error", JOptionPane.ERROR_MESSAGE);
                    this.dispose();
                    new SearchSubAgentFrm(currentuser).setVisible(true);
                }
            }
        }
    }
    
    public static void main(String[] args) {

    }
}
