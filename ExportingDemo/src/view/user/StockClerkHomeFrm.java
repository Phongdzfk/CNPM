package view.user;

import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StockClerkHomeFrm extends JFrame implements ActionListener {
    private User u;
    private JButton btnExportProducts;

    public StockClerkHomeFrm(User u) {
        super("Stock Clerk Home");
        this.u = u;

        // Main panel
        JPanel pnMain = new JPanel();
        pnMain.setLayout(new BoxLayout(pnMain, BoxLayout.PAGE_AXIS));
        pnMain.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        pnMain.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel lblTitle = new JLabel("Stock Clerk Home");
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitle.setFont(lblTitle.getFont().deriveFont(22.0f));
        pnMain.add(lblTitle);
        pnMain.add(Box.createRigidArea(new Dimension(0, 30)));

        JPanel pnButton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnExportProducts = new JButton("Export Product");
        btnExportProducts.setPreferredSize(new Dimension(200, 40));
        btnExportProducts.setFont(new Font("SansSerif", Font.PLAIN, 16));
        btnExportProducts.addActionListener(this);
        pnButton.add(btnExportProducts);
        pnMain.add(pnButton);

        pnMain.add(Box.createRigidArea(new Dimension(0, 10)));

        this.setContentPane(pnMain);
        this.setSize(500, 250);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ((e.getSource() instanceof JButton) && (e.getSource()).equals(btnExportProducts)) {
            this.dispose();
            new SearchSubAgentFrm(u).setVisible(true);
        }
    }

    public static void main(String[] args) {

    }
}
