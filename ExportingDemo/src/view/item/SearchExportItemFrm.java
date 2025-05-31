package view.item;

import dao.ItemDAO;
import model.ExportBill;
import model.Item;
import view.user.SearchSubAgentFrm;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class SearchExportItemFrm extends JFrame implements ActionListener {
    private ExportBill bill;
    private JTextField txtSearch;
    private JButton btnSearch;
    private JButton btnBack;
    private JButton btnNext;
    private JTable tblResults;
    private DefaultTableModel tableModel;

    private static final String[] COLUMNS = {"ID", "Name", "Description", "Stock", "unitPrice", "Select"};

    public SearchExportItemFrm(ExportBill bill) {
        this.bill = bill;
        txtSearch = new JTextField(25);
        btnSearch = new JButton("Search");
        btnBack = new JButton("Back");
        btnNext = new JButton("Next");

        tableModel = new DefaultTableModel(COLUMNS, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5;
            }

            @Override
            public Class<?> getColumnClass(int column) {
                return column == 5 ? JButton.class : Object.class;
            }
        };

        tblResults = new JTable(tableModel);
        tblResults.setRowHeight(60);
        tblResults.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblResults.getColumnModel().getColumn(2).setCellRenderer(new MultiLineCellRenderer());

        setLayout(new BorderLayout(10, 10));

        // Search panel
        JPanel searchPanel = new JPanel();
        searchPanel.add(new JLabel("Search item:"));
        searchPanel.add(txtSearch);
        searchPanel.add(btnSearch);
        add(searchPanel, BorderLayout.NORTH);

        // Table
        add(new JScrollPane(tblResults), BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new BorderLayout());
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        leftPanel.add(btnBack);
        rightPanel.add(btnNext);
        buttonPanel.add(leftPanel, BorderLayout.WEST);
        buttonPanel.add(rightPanel, BorderLayout.EAST);
        add(buttonPanel, BorderLayout.SOUTH);

        btnSearch.addActionListener(this);
        btnBack.addActionListener(this);
        btnNext.addActionListener(this);

        setTitle("Search Export Item - Agent: " + bill.getS().getBrandName());
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        addSelectButtonColumn();
    }

    private void addSelectButtonColumn() {
        TableColumn selectColumn = tblResults.getColumnModel().getColumn(5);
        selectColumn.setCellRenderer(new ButtonRenderer());
        selectColumn.setCellEditor(new ButtonEditor(new JCheckBox()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ((e.getSource() instanceof JButton) && ((JButton)e.getSource()).equals(btnSearch)) {
            String searchtext = txtSearch.getText().trim();
            ItemDAO itd = new ItemDAO();
            List<Item> exportitem = itd.searchExportItem(searchtext);
            if (exportitem.isEmpty()) JOptionPane.showMessageDialog(this, "There are no items available", "NotFound", JOptionPane.ERROR_MESSAGE);
            tableModel.setRowCount(0);
            int id = 1;
            for (Item i : exportitem) {
                tableModel.addRow(new Object[]{
                        id,
                        i.getItemName(),
                        i.getDescription(),
                        i.getStockQuantity(),
                        i.getUnitPrice(),
                        "Select"
                });
                id++;
            }
        } else if ((e.getSource() instanceof JButton) && (e.getSource()).equals(btnBack)) {
            this.dispose();
            new SearchSubAgentFrm(this.bill.getU()).setVisible(true);
        } else if ((e.getSource() instanceof JButton) && (e.getSource()).equals(btnNext)) {
            this.dispose();
            new ConfirmFrm(this.bill).setVisible(true);
        }
    }

    // Inner classes for table components
    private static class MultiLineCellRenderer extends JTextArea implements TableCellRenderer {
        public MultiLineCellRenderer() {
            setLineWrap(true);
            setWrapStyleWord(true);
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText(value.toString());
            setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
            setForeground(isSelected ? table.getSelectionForeground() : table.getForeground());
            return this;
        }
    }

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
                Item selectedItem = new Item();
                selectedItem.setID((Integer) currentData[0]);
                selectedItem.setItemName((String) currentData[1]);
                selectedItem.setDescription((String) currentData[2]);
                selectedItem.setStockQuantity((Integer) currentData[3]);
                selectedItem.setUnitPrice((Double) currentData[4]);
                new AddExportItemFrm(bill,selectedItem).setVisible(true);
            });
        }

        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            currentData = new Object[]{
                    table.getValueAt(row, 0),
                    table.getValueAt(row, 1),
                    table.getValueAt(row, 2),
                    table.getValueAt(row, 3),
                    table.getValueAt(row, 4)
            };
            return button;
        }
    }

    public static void main(String[] args) {

    }
}
