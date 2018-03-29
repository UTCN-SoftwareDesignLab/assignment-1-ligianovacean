package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.text.View.Y_AXIS;

public class RUDView extends JFrame{

    private JButton btnView;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JLabel lblEnterUniqueIdentifier;
    private JTextField tfUniqueIdentifier;
    private JTable tbl;
    private JScrollPane scrollPane;

    public RUDView() {
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
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void initializeFields() {
        lblEnterUniqueIdentifier = new JLabel("Enter Identifier");
        tfUniqueIdentifier = new JTextField();
        tfUniqueIdentifier.setSize(150, 50);
        tbl = new JTable();
        scrollPane = new JScrollPane(tbl);
        tbl.setSize(200, 10);
        btnView = new JButton("View");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
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

    public List<String> getSelectedRow() {
        List<String> selection = new ArrayList<>();

        return selection;
    }

    public void loadTable(JTable tbl) {
        scrollPane.setViewportView(tbl);
        revalidate();
        repaint();
    }
}
