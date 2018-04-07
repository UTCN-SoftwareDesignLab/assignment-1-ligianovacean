package view;

import javax.swing.*;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.text.View.Y_AXIS;

public class CreateAccountView extends View {

    private JTable tblClients;
    private JLabel lblClientsTable;
    private JScrollPane scrollPane;
    private JLabel lblType;
    private JLabel lblDate;
    private JTextField tfType;
    private JTextField tfDate;
    private JButton btnCreate;

    public CreateAccountView() {
        this.setTitle("Create Account Page");
        setSize(350, 300);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        add(lblClientsTable);
        add(scrollPane);
        add(tblClients);
        add(lblType);
        add(tfType);
        add(lblDate);
        add(tfDate);
        add(btnCreate);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(false);
    }

    private void initializeFields() {
        lblClientsTable = new JLabel("Choose a client: ");
        tblClients = new JTable();
        scrollPane  = new JScrollPane(tblClients);
        lblType = new JLabel("Account type(Savings account/Spending account)");
        tfType = new JTextField();
        lblDate = new JLabel("Date of creation(E.g. March 3, 2017)");
        tfDate = new JTextField();
        btnCreate = new JButton("Create Account");
    }

    public String getAccountType() {
        return tfType.getText();
    }

    public String getCreationDate() {
        return tfDate.getText();
    }

    public void setCreateButtonActionListener(ActionListener createButtonActionListener){
        btnCreate.addActionListener(createButtonActionListener);
    }

    public Long getSelectedRow() {
        return Long.parseLong(tblClients.getValueAt(tblClients.getSelectedRow(), 0).toString());
    }

    public void loadTable(JTable tbl) {
        this.tblClients = tbl;
        scrollPane.setViewportView(tbl);
        revalidate();
        repaint();
    }

}
