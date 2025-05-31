package view.user;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import dao.UserDAO;
import model.User;

public class LoginFrm extends JFrame implements ActionListener {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;

    public LoginFrm() {
        super("Login");
        txtUsername = new JTextField(15);
        txtPassword = new JPasswordField(15);
        txtPassword.setEchoChar('*');
        btnLogin = new JButton("Login");

        JPanel pnMain = new JPanel();
        pnMain.setSize(this.getSize().width - 5, this.getSize().height - 20);
        pnMain.setLayout(new BoxLayout(pnMain, BoxLayout.PAGE_AXIS));
        pnMain.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel lblHome = new JLabel("Login");
        lblHome.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblHome.setFont(lblHome.getFont().deriveFont(20.0f));
        pnMain.add(lblHome);
        pnMain.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel pnUsername = new JPanel(new FlowLayout());
        pnUsername.add(new JLabel("Username:"));
        pnUsername.add(txtUsername);
        pnMain.add(pnUsername);

        JPanel pnPass = new JPanel(new FlowLayout());
        pnPass.add(new JLabel("Password:"));
        pnPass.add(txtPassword);
        pnMain.add(pnPass);

        pnMain.add(btnLogin);
        pnMain.add(Box.createRigidArea(new Dimension(0, 10)));
        btnLogin.addActionListener(this);

        this.setSize(400, 200);
        this.setLocationRelativeTo(null);
        this.setContentPane(pnMain);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ((e.getSource() instanceof JButton) && (e.getSource()).equals(btnLogin)) {
            User user = new User();
            user.setUsername(txtUsername.getText());
            user.setPassword(new String(txtPassword.getPassword()));

            UserDAO ud = new UserDAO();
            if (ud.checkLogin(user)) {
                String role = user.getRole();
                if (role != null && role.equalsIgnoreCase("stockclerk")) {
                    new StockClerkHomeFrm(user).setVisible(true);
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "The function of the role " + role + " is under construction!");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Incorrect username and/or password!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        LoginFrm myFrame = new LoginFrm();
        myFrame.setVisible(true);
    }
}