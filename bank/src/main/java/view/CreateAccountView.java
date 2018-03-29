package view;

import javax.swing.*;

import java.awt.event.ActionListener;

import static javax.swing.text.View.Y_AXIS;

public class CreateAccountView extends JFrame {

    private JTable tblClients;
    private JLabel lblClientsTable;
    private JLabel lblType;
    private JLabel lblDate;
    private JTextField tfType;
    private JTextField tfDate;
    private JButton btnCreate;

    public CreateAccountView(String[] columnNames, Object[][] data) {
        setSize(300, 300);
        setLocationRelativeTo(null);
        initializeFields(columnNames, data);
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        add(lblClientsTable);
        add(tblClients);
        add(lblType);
        add(tfType);
        add(lblDate);
        add(tfDate);
        add(btnCreate);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void initializeFields(String[] columnNames, Object[][] data) {
        lblClientsTable = new JLabel("Choose a client:");
        tblClients = new JTable(data, columnNames);
        lblType = new JLabel("Account type");
        tfType = new JTextField();
        lblDate = new JLabel("Date of creation");
        tfDate = new JTextField();
        btnCreate = new JButton("Create Account");
    }

    public String getAccountType() {
        return tfType.getText();
    }

    public String getCreationDate() {
        return tfType.getText();
    }

    public Integer getClientId() {
        return (Integer)tblClients.getValueAt(tblClients.getSelectedRow(), 0);
    }

    public void setCreateButtonActionListener(ActionListener createButtonActionListener){
        btnCreate.addActionListener(createButtonActionListener);
    }

}
