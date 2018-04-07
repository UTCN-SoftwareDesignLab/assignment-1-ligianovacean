package view;

import javax.swing.*;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static javax.swing.text.View.Y_AXIS;

public class ProcessUtilityBillsView extends View {

    private JLabel lblClientsTable;
    private JTable tblClients;
    private JScrollPane scrollPaneClients;
    private JButton btnViewClientAccounts;
    private JLabel lblAccountsTable;
    private JTable tblAccounts;
    private JScrollPane scrollPaneAccounts;
    private JLabel lblIdentifier;
    private JTextField tfIdentifier;
    private JLabel lblSum;
    private JTextField tfSum;
    private JButton btnProcess;

    public ProcessUtilityBillsView() {
        this.setTitle("Process Utility Bills Page");
        setSize(300, 400);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        add(lblClientsTable);
        add(scrollPaneClients);
        add(btnViewClientAccounts);
        add(lblAccountsTable);
        add(scrollPaneAccounts);
        add(lblIdentifier);
        add(tfIdentifier);
        add(lblSum);
        add(tfSum);
        add(btnProcess);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(false);
    }

    private void initializeFields() {
        lblClientsTable = new JLabel("Choose a client:");
        tblClients = new JTable();
        scrollPaneClients = new JScrollPane(tblClients);
        btnViewClientAccounts = new JButton("View Client Accounts");
        lblAccountsTable = new JLabel("Choose an account for payment:");
        tblAccounts = new JTable();
        scrollPaneAccounts = new JScrollPane(tblAccounts);
        lblIdentifier = new JLabel("Enter bill identifier:");
        tfIdentifier = new JTextField();
        lblSum = new JLabel("Enter bill value:");
        tfSum = new JTextField();
        btnProcess = new JButton("Finish payment");
    }

    public String getSum() {
        return tfSum.getText();
    }

    public String getIdentifier(){
        return tfIdentifier.getText();
    }

    public List<Object> getSelectedClient() {
        List<Object> selection = new ArrayList<>();
        int row = tblClients.getSelectedRow();
        int columnCount = tblClients.getColumnCount();
        for (int i = 0; i < columnCount; i++) {
            selection.add(tblClients.getValueAt(row, i));
        }
        return selection;
    }

    public List<Object> getSelectedAccount() {
        List<Object> selection = new ArrayList<>();
        int row = tblAccounts.getSelectedRow();
        int columnCount = tblAccounts.getColumnCount();
        if (row == -1) {
            return null;
        }
        for (int i = 0; i < columnCount; i++) {
            selection.add(tblAccounts.getValueAt(row, i));
        }
        return selection;
    }

    public void loadClientsTable(JTable tbl) {
        this.tblClients = tbl;
        scrollPaneClients.setViewportView(tbl);
        revalidate();
        repaint();
    }

    public void loadAccountsTable(JTable tbl) {
        this.tblAccounts = tbl;
        scrollPaneAccounts.setViewportView(tbl);
        revalidate();
        repaint();
    }

    public void setBtnViewAccountsActionListener(ActionListener btnViewBillActionListener){
        btnViewClientAccounts.addActionListener(btnViewBillActionListener);
    }

    public void setBtnProcessActionListener(ActionListener btnProcessActionListener){
        btnProcess.addActionListener(btnProcessActionListener);
    }
}
