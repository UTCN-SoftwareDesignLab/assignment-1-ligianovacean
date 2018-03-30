package view;

import javax.swing.*;

import java.awt.event.ActionListener;

import static javax.swing.text.View.Y_AXIS;

public class CreateEmployeeView extends JFrame {

    private JLabel lblUsername;
    private JLabel lblPassword;
    private JTextField tfUsername;
    private JTextField tfPassword;
    private JButton btnCreate;

    public CreateEmployeeView() {
        setSize(300, 300);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        add(lblUsername);
        add(tfUsername);
        add(lblPassword);
        add(tfPassword);
        add(btnCreate);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(false);
    }

    private void initializeFields() {
        lblUsername = new JLabel("Username");
        lblPassword = new JLabel("Password");
        tfUsername = new JTextField();
        tfPassword = new JTextField();
        btnCreate = new JButton("Create Employee");
    }

    private String getUsername() {
        return tfUsername.getText();
    }

    private String getPassword() {
        return tfPassword.getText();
    }

    private void setCreateButtonActionListener(ActionListener buttonActionListener) {
        btnCreate.addActionListener(buttonActionListener);
    }

    public void setVisibility(Boolean bool) {
        if (bool) {
            setVisible(true);
        } else {
            setVisible(false);
        }
    }

}
