package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import static javax.swing.text.View.Y_AXIS;

public class EmployeeView extends JFrame {

    private JRadioButton rbAddClient;
    private JRadioButton rbUpdateClient;
    private JRadioButton rbViewClient;
    private JRadioButton rbCreateAccount;
    private JRadioButton rbUpdateAccount;
    private JRadioButton rbViewAccount;
    private JRadioButton rbDeleteAccount;
    private JRadioButton rbTransferMoney;
    private JRadioButton rbProcessUtilityBills;
    private ButtonGroup btnGroup;
    private JButton btnConfirmSelection;

    public EmployeeView() throws HeadlessException {
        setSize(300, 300);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        add(rbAddClient);
        add(rbUpdateClient);
        add(rbViewClient);
        add(rbCreateAccount);
        add(rbUpdateAccount);
        add(rbViewAccount);
        add(rbDeleteAccount);
        add(rbTransferMoney);
        add(rbProcessUtilityBills);
        add(btnConfirmSelection);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void initializeFields() {
        rbAddClient = new JRadioButton("Add Client");
        rbUpdateClient = new JRadioButton("Update Client");
        rbViewClient = new JRadioButton("View Client");
        rbCreateAccount = new JRadioButton("Create Account");
        rbUpdateAccount = new JRadioButton("Update Account");
        rbViewAccount = new JRadioButton("View Account");
        rbDeleteAccount = new JRadioButton("Delete account");
        rbTransferMoney = new JRadioButton("Transfer money");
        rbProcessUtilityBills = new JRadioButton("Process utility bills");
        btnGroup = new ButtonGroup();
        btnGroup.add(rbAddClient);
        btnGroup.add(rbUpdateClient);
        btnGroup.add(rbViewClient);
        btnGroup.add(rbCreateAccount);
        btnGroup.add(rbUpdateAccount);
        btnGroup.add(rbViewAccount);
        btnGroup.add(rbDeleteAccount);
        btnGroup.add(rbTransferMoney);
        btnGroup.add(rbProcessUtilityBills);
        btnConfirmSelection = new JButton("Confirm Selection");
    }

    public String getSelection() {
        if (rbAddClient.isSelected())
            return "Add Client";
        if (rbUpdateClient.isSelected())
            return "Update Client";
        if (rbViewClient.isSelected())
            return "ViewClient";
        if (rbCreateAccount.isSelected())
            return "CreateAccount";
        if (rbViewAccount.isSelected())
            return "ViewAccount";
        if (rbUpdateAccount.isSelected())
            return "UpdateAccount";
        if (rbDeleteAccount.isSelected())
            return "DeleteAccount";
        if (rbTransferMoney.isSelected())
            return "TransferMoney";
        if (rbProcessUtilityBills.isSelected())
            return "ProcessUtilityBills";
        return "";
    }

    public void setConfirmSelectionButtonListener(ActionListener confirmSelectionButtonListener) {
        btnConfirmSelection.addActionListener(confirmSelectionButtonListener);
    }

}
