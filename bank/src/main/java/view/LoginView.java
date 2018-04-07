package view;

import database.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static database.Constants.Roles.ADMINISTRATOR;
import static database.Constants.Roles.EMPLOYEE;

import static javax.swing.BoxLayout.Y_AXIS;

public class LoginView extends View {

    private JLabel lblUsername;
    private JLabel lblPassword;
    private JTextField tfUsername;
    private JTextField tfPassword;
    private JButton btnLogin;
    private JButton btnRegisterAdmin;
    private JButton btnRegisterEmployee;
    private JButton btnLogout;

    public LoginView() throws HeadlessException {
        this.setTitle("Login Page");
        setSize(300, 300);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        add(lblUsername);
        add(tfUsername);
        add(lblPassword);
        add(tfPassword);
        add(btnLogin);
        add(btnRegisterAdmin);
        add(btnRegisterEmployee);
        add(btnLogout);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void initializeFields() {
        tfUsername = new JTextField();
        tfPassword = new JTextField();
        btnLogin = new JButton("Login");
        btnRegisterAdmin = new JButton("Register as Admin");
        btnRegisterAdmin.setActionCommand(ADMINISTRATOR);
        btnRegisterEmployee = new JButton("Register as Employee");
        btnRegisterEmployee.setActionCommand(EMPLOYEE);
        lblUsername = new JLabel("Username");
        lblPassword = new JLabel("Password");
        btnLogout = new JButton("Logout");
    }

    public String getUsername() {
        return tfUsername.getText();
    }

    public String getPassword() {
        return tfPassword.getText();
    }

    public void setLoginButtonListener(ActionListener loginButtonListener) {
        btnLogin.addActionListener(loginButtonListener);
    }

    public void setRegisterButtonListener(ActionListener registerButtonListener) {
        btnRegisterAdmin.addActionListener(registerButtonListener);
        btnRegisterEmployee.addActionListener(registerButtonListener);
    }

    public void setLogoutButtonListener(ActionListener logoutButtonListener) {
        btnLogout.addActionListener(logoutButtonListener);
    }

}
