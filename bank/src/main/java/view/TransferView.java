package view;

import javax.swing.*;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.text.View.Y_AXIS;

public class TransferView extends View {

    private JLabel lblClientIdentifier1;
    private JLabel lblAccount1;
    private JLabel lblAccount2;
    private JLabel lblSum;;
    private JTable tblClients;
    private JTable tblClientsAccounts;
    private JTextField tfAccount2;
    private JTextField tfSum;
    private JButton btnTransfer;
    private JScrollPane scrollPaneClients;
    private JScrollPane scrollPaneAccounts;
    private JButton btnViewClientAccounts;

    public TransferView() {
        this.setTitle("Transfer Page");
        setSize(300, 500);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        add(lblClientIdentifier1);
        add(scrollPaneClients);
        add(btnViewClientAccounts);
        add(lblAccount1);
        add(scrollPaneAccounts);
        add(lblAccount2);
        add(tfAccount2);
        add(lblSum);
        add(tfSum);
        add(btnTransfer);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(false);
    }

    private void initializeFields() {
        lblAccount1 = new JLabel("Choose the first account");
        lblAccount2 = new JLabel("Second Account Identifier");
        lblClientIdentifier1 = new JLabel("Choose the client");
        lblSum = new JLabel("Enter sum to be transfered");
        tfAccount2 = new JTextField();
        tfSum = new JTextField();
        tblClients = new JTable();
        tblClientsAccounts = new JTable();
        scrollPaneAccounts = new JScrollPane(tblClientsAccounts);
        scrollPaneClients = new JScrollPane(tblClients);
        btnViewClientAccounts = new JButton("View Client Accounts");
        btnTransfer = new JButton("Perform Transfer");
    }


    public String getAccount2() {
        return tfAccount2.getText();
    }

    public String getSum() {
        return tfSum.getText();
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
        int row = tblClientsAccounts.getSelectedRow();
        int columnCount = tblClientsAccounts.getColumnCount();
        for (int i = 0; i < columnCount; i++) {
            selection.add(tblClientsAccounts.getValueAt(row, i));
        }
        return selection;
    }

    public void setBtnTransferActionListener(ActionListener btnTransferActionListener) {
        btnTransfer.addActionListener(btnTransferActionListener);
    }

    public void setBtnViewClientAccounts(ActionListener btnViewClientAccountsListener) {
        btnViewClientAccounts.addActionListener(btnViewClientAccountsListener);
    }

    public void loadClientsTable(JTable tbl) {
        this.tblClients = tbl;
        scrollPaneClients.setViewportView(tbl);
        revalidate();
        repaint();
    }

    public void loadAccountsTable(JTable tbl) {
        this.tblClientsAccounts = tbl;
        scrollPaneAccounts.setViewportView(tbl);
        revalidate();
        repaint();
    }

}
