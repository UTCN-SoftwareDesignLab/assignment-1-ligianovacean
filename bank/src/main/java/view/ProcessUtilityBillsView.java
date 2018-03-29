package view;

import javax.swing.*;

import java.awt.event.ActionListener;
import java.util.Random;

import static javax.swing.text.View.Y_AXIS;

public class ProcessUtilityBillsView extends JFrame {

    private JLabel lblBill;
    private JTextField tfBill;
    private JButton btnViewBill;
    private JTextField tfBillInfo;
    private JLabel lblAccount;
    private JTextField tfAccount;
    private JButton btnProcess;

    public ProcessUtilityBillsView() {
        setSize(300, 300);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        add(lblBill);
        add(tfBill);
        add(btnViewBill);
        add(tfBillInfo);
        add(lblAccount);
        add(tfAccount);
        add(btnProcess);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void initializeFields() {
        lblBill = new JLabel("Enter Bill Code");
        tfBill = new JTextField();
        btnViewBill = new JButton("Process Bill");
        tfBillInfo = new JTextField();
        lblAccount = new JLabel("Account used for payment");
        tfAccount = new JTextField();
        btnProcess = new JButton("Finish payment");
    }

    public void setBillInfo(Integer upperLimit) {
        Random random = new Random();
        tfBillInfo.setText(random.nextInt(upperLimit)+"");
    }

    public String getBillSum() {
        return tfBillInfo.getText();
    }

    public String getAccount() {
        return tfAccount.getText();
    }

    public void setBtnViewBillActionListener(ActionListener btnViewBillActionListener){
        btnViewBill.addActionListener(btnViewBillActionListener);
    }

    //if the process button is not pressed(no value in the test field area), we should generate here the sum
    public void setBtnProcessActionListener(ActionListener btnProcessActionListener){
        btnProcess.addActionListener(btnProcessActionListener);
    }

}
