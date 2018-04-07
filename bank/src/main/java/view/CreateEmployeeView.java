package view;

import javax.swing.*;

import java.awt.event.ActionListener;

import static javax.swing.text.View.Y_AXIS;

public class CreateEmployeeView extends View{

    private JLabel lblUsername;
    private JLabel lblPassword;
    private JTextField tfUsername;
    private JTextField tfPassword;
    private JButton btnCreate;

    public CreateEmployeeView() {
        this.setTitle("Create Employee Page");
        setSize(300, 300);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        add(lblUsername);
        add(tfUsername);
        add(lblPassword);
        add(tfPassword);
        add(btnCreate);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(false);
    }

    private void initializeFields() {
        lblUsername = new JLabel("Username");
        lblPassword = new JLabel("Password");
        tfUsername = new JTextField();
        tfPassword = new JTextField();
        btnCreate = new JButton("Create Employee");
    }

    public String getUsername() {
        return tfUsername.getText();
    }

    public String getPassword() {
        return tfPassword.getText();
    }

    public void setCreateButtonActionListener(ActionListener buttonActionListener) {
        btnCreate.addActionListener(buttonActionListener);
    }
}
