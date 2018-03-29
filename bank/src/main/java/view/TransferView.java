package view;

import javax.swing.*;

import java.awt.event.ActionListener;

import static javax.swing.text.View.Y_AXIS;

public class TransferView extends JFrame {

    private JLabel lblClientIdentifier1;
    private JLabel lblClientIdentifier2;
    private JLabel lblAccount1;
    private JLabel lblAccount2;
    private JLabel lblSum;
    private JTextField tfClient1;
    private JTextField tfClient2;
    private JTextField tfAccount1;
    private JTextField tfAccount2;
    private JTextField tfSum;
    private JButton btnTransfer;

    public TransferView() {
        setSize(300, 300);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        add(lblClientIdentifier1);
        add(tfClient1);
        add(lblAccount1);
        add(tfAccount1);
        add(lblClientIdentifier2);
        add(tfClient2);
        add(lblAccount2);
        add(tfAccount2);
        add(lblSum);
        add(tfSum);
        add(btnTransfer);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void initializeFields() {
        lblAccount1 = new JLabel("First Account Identifier");
        lblAccount2 = new JLabel("Second Account Identifier");
        lblClientIdentifier1 = new JLabel("First Client Identifier");
        lblClientIdentifier2 = new JLabel("Second Client Identifier");
        lblSum = new JLabel("Enter sum to be transfered");
        tfAccount1 = new JTextField();
        tfAccount2 = new JTextField();
        tfClient1 = new JTextField();
        tfClient2 = new JTextField();
        tfSum = new JTextField();
        btnTransfer = new JButton("Perform Transfer");
    }

    public String getClient1() {
        return tfClient1.getText();
    }

    public String getClient2() {
        return tfClient2.getText();
    }

    public String getAccount1() {
        return tfAccount1.getText();
    }

    public String getAccount2() {
        return tfAccount2.getText();
    }

    public Integer getSum() {
        return Integer.parseInt(tfSum.getText());
    }

    public void setBtnTransferActionListener(ActionListener btnTransferActionListener) {
        btnTransfer.addActionListener(btnTransferActionListener);
    }
}
