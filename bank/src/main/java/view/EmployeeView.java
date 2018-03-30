package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import static javax.swing.text.View.Y_AXIS;

public class EmployeeView extends JFrame {

    private JRadioButton rbAddClient;
    private JRadioButton rbUpdateClient;
    private JRadioButton rbCreateAccount;
    private JRadioButton rbUpdateAccount;
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
        add(rbCreateAccount);
        add(rbUpdateAccount);
        add(rbTransferMoney);
        add(rbProcessUtilityBills);
        add(btnConfirmSelection);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(false);
    }

    private void initializeFields() {
        rbAddClient = new JRadioButton("Add Client");
        rbUpdateClient = new JRadioButton("Update/View Client");
        rbCreateAccount = new JRadioButton("Create Account");
        rbUpdateAccount = new JRadioButton("Update/Delete/View Account");
        rbTransferMoney = new JRadioButton("Transfer money");
        rbProcessUtilityBills = new JRadioButton("Process utility bills");
        btnGroup = new ButtonGroup();
        btnGroup.add(rbAddClient);
        btnGroup.add(rbUpdateClient);
        btnGroup.add(rbCreateAccount);
        btnGroup.add(rbUpdateAccount);
        btnGroup.add(rbTransferMoney);
        btnGroup.add(rbProcessUtilityBills);
        btnConfirmSelection = new JButton("Confirm Selection");
    }

    public String getSelection() {
        if (rbAddClient.isSelected())
            return "AddClient";
        if (rbUpdateClient.isSelected())
            return "RUDClient";
        if (rbCreateAccount.isSelected())
            return "CreateAccount";
        if (rbUpdateAccount.isSelected())
            return "RUDAccount";
        if (rbTransferMoney.isSelected())
            return "TransferMoney";
        if (rbProcessUtilityBills.isSelected())
            return "ProcessUtilityBills";
        return "";
    }

    public void setConfirmSelectionButtonListener(ActionListener confirmSelectionButtonListener) {
        btnConfirmSelection.addActionListener(confirmSelectionButtonListener);
    }

    public void setVisibility(Boolean bool) {
        if (bool) {
            setVisible(true);
        } else {
            setVisible(false);
        }
    }

}
