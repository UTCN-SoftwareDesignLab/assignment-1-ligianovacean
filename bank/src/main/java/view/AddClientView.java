package view;

import javax.swing.*;

import java.awt.event.ActionListener;

import static javax.swing.text.View.Y_AXIS;

public class AddClientView extends JFrame{

    private JLabel lblName;
    private JLabel lblIdCardNo;
    private JLabel lblPnc;
    private JLabel lblAddress;
    private JTextField tfName;
    private JTextField tfIdCardNo;
    private JTextField tfPersonalNumericalCode;
    private JTextField tfAddress;
    private JButton btnSaveChanges;

    public AddClientView() {
        setSize(300, 300);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        add(lblName);
        add(tfName);
        add(lblIdCardNo);
        add(tfIdCardNo);
        add(lblPnc);
        add(tfPersonalNumericalCode);
        add(lblAddress);
        add(tfAddress);
        add(btnSaveChanges);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void initializeFields() {
        lblAddress = new JLabel("Client address");
        lblIdCardNo  =new JLabel("Id card number");
        lblName = new JLabel("Client name");
        lblPnc = new JLabel("Client personal numerical code");
        tfAddress = new JTextField();
        tfIdCardNo = new JTextField();
        tfName = new JTextField();
        tfPersonalNumericalCode = new JTextField();
        btnSaveChanges = new JButton("Save Changes");
    }

    public void setSaveChangesButtonListener(ActionListener saveChangesButtonListener) {
        btnSaveChanges.addActionListener(saveChangesButtonListener);
    }

    public String getName() {
        return tfName.getText();
    }

    public String getIdCardNo() {
        return tfIdCardNo.getText();
    }

    public String getPersonalNumericalCode() {
        return tfPersonalNumericalCode.getText();
    }

    public String getAddress() {
        return tfAddress.getText();
    }
}
