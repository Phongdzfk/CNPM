package view.user;

import model.ExportBill;
import model.SubAgent;
import model.User;
import dao.SubAgentDAO;
import view.item.SearchExportItemFrm;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import java.util.List;

public class SearchSubAgentFrm extends JFrame implements ActionListener {
    private User currentUser;
    private JTextField txtSearch;
    private JButton btnSearch;
    private JButton btnBack;
    private JTable tblResults;
    private DefaultTableModel tableModel;

    private static final String[] COLUMNS = {"ID", "Brand Name", "Address", "Phone", "Select"};

    public SearchSubAgentFrm(User user) {
        this.currentUser = user;
        txtSearch = new JTextField(25);
        btnSearch = new JButton("Search");
        btnBack = new JButton("Back");

        tableModel = new DefaultTableModel(COLUMNS, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4;
            }

            @Override
            public Class<?> getColumnClass(int column) {
                return column == 4 ? JButton.class : Object.class;
            }
        };
        tblResults = new JTable(tableModel);
        tblResults.setRowHeight(30);
        setLayout(new BorderLayout(10, 10));

        // Search panel
        JPanel searchPanel = new JPanel();
        searchPanel.add(new JLabel("Search sub-agent:"));
        searchPanel.add(txtSearch);
        searchPanel.add(btnSearch);
        searchPanel.add(btnBack);

        add(searchPanel, BorderLayout.NORTH);

        // Table
        add(new JScrollPane(tblResults), BorderLayout.CENTER);

        // Button actions
        btnSearch.addActionListener(this);
        btnBack.addActionListener(this);

        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addSelectButtonColumn();
        setTitle("Search Sub-agent - User: " + user.getUsername());
    }

    private void addSelectButtonColumn() {
        TableColumn selectColumn = tblResults.getColumnModel().getColumn(4);
        selectColumn.setCellRenderer(new ButtonRenderer());
        selectColumn.setCellEditor(new ButtonEditor(new JCheckBox()));
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if((e.getSource() instanceof JButton) &&(e.getSource()).equals(btnSearch)) {
            String searchText = txtSearch.getText().trim();
            SubAgentDAO sad = new SubAgentDAO();
            List<SubAgent> agents = sad.searchSubAgentByName(searchText);
            if (agents.isEmpty())
            {
                JOptionPane.showMessageDialog(this, "There are no agent like that, please add new one", "Error", JOptionPane.ERROR_MESSAGE);
                this.dispose();
                new AddSubAgentFrm(currentUser).setVisible(true);
            }
            tableModel.setRowCount(0);
            int id = 1;
            for(SubAgent agent : agents) {
                tableModel.addRow(new Object[]{
                        id,
                        agent.getBrandName(),
                        agent.getAddress(),
                        agent.getPhoneNumber(),
                        "Select"
                });
                id++;
            }
        }
        else if((e.getSource() instanceof JButton) && (e.getSource()).equals(btnBack)) {
            this.dispose();
            new StockClerkHomeFrm(currentUser).setVisible(true);
        }
    }
    // Inner classes for button column
    private static class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText("Select");
            return this;
        }
    }

    private class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private Object[] currentData;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton("Select");
            button.addActionListener(e -> {
                fireEditingStopped();
                SubAgent selectedAgent = new SubAgent();
                selectedAgent.setID((Integer) currentData[0]);
                selectedAgent.setBrandName((String) currentData[1]);
                selectedAgent.setAddress((String) currentData[2]);
                selectedAgent.setPhoneNumber((String) currentData[3]);
                ExportBill b = new ExportBill();
                b.setU(currentUser);
                b.setS(selectedAgent);
                b.setDateIssued(new Date());

                new SearchExportItemFrm(b).setVisible(true);
                dispose();
            });
        }

        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            currentData = new Object[]{
                    table.getValueAt(row, 0),
                    table.getValueAt(row, 1),
                    table.getValueAt(row, 2),
                    table.getValueAt(row, 3)
            };
            return button;
        }
    }

    public static void main(String[] args) {

    }
}
