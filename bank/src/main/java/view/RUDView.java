package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.text.View.Y_AXIS;

public class RUDView extends View {

    private JButton btnView;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JLabel lblEnterUniqueIdentifier;
    private JTextField tfUniqueIdentifier;
    private JTable tbl;
    private JScrollPane scrollPane;

    public RUDView() {
        this.setTitle("Read/Update/[Delete] Operations' Page");
        setSize(300, 300);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        add(lblEnterUniqueIdentifier);
        add(tfUniqueIdentifier);
        add(scrollPane);
        add(btnView);
        add(btnUpdate);
        add(btnDelete);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(false);
    }

    private void initializeFields() {
        lblEnterUniqueIdentifier = new JLabel("Enter Identifier(ID)");
        tfUniqueIdentifier = new JTextField();
        tfUniqueIdentifier.setSize(150, 50);
        tbl = new JTable();
        scrollPane = new JScrollPane(tbl);
        tbl.setSize(200, 10);
        btnView = new JButton("View");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
    }

    public void setIdentifierLabel() {
        lblEnterUniqueIdentifier.setText("Enter Identifier(Username)");
    }

    public void resetDelete() {
        btnDelete.setVisible(false);
    }

    public void setBtnUpdateListener(ActionListener btnSaveChangesListener) {
        btnUpdate.addActionListener(btnSaveChangesListener);
    }

    public void setBtnDeleteListener(ActionListener btnSaveChangesListener) {
        btnDelete.addActionListener(btnSaveChangesListener);
    }

    public void setBtnViewListener(ActionListener btnSaveChangesListener) {
        btnView.addActionListener(btnSaveChangesListener);
    }

    public String getUniqueIdentifier() {
        return tfUniqueIdentifier.getText();
    }

    public List<Object> getSelectedRow() {
        List<Object> selection = new ArrayList<>();
        int row = tbl.getSelectedRow();
        int columnCount = tbl.getColumnCount();
        for (int i = 0; i < columnCount; i++) {
            selection.add(tbl.getValueAt(row, i));
        }
        return selection;
    }

    public void loadTable(JTable tbl) {
        this.tbl = tbl;
        scrollPane.setViewportView(tbl);
        revalidate();
        repaint();
    }
}
