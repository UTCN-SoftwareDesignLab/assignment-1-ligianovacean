package controller;

import model.User;
import model.validation.Notification;
import repository.user.AuthenticationException;
import service.user.AuthenticationService;
import view.LoginView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController implements Controller{

    private final LoginView loginView;
    private final AuthenticationService authenticationService;

    private final EmployeeController employeeController;

    public LoginController(LoginView loginView, AuthenticationService authenticationService, EmployeeController employeeController) {
        this.loginView = loginView;
        this.authenticationService = authenticationService;
        this.employeeController = employeeController;
        loginView.setLoginButtonListener(new LoginButtonListener());
        loginView.setRegisterButtonListener(new RegisterButtonListener());
    }

    @Override
    public Notification<Controller> getNextController(String selection) {
        Notification<Controller> nextControllerNotification = new Notification<>();
        nextControllerNotification.setResult(employeeController);
        return nextControllerNotification;
    }

    @Override
    public void setVisibility(Boolean bool) {
        loginView.setVisibility(bool);
    }

    private class LoginButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            Notification<User> loginNotification = null;
            try {
                loginNotification = authenticationService.login(username, password);
            } catch (AuthenticationException e1) {
                e1.printStackTrace();
            }

            if (loginNotification != null) {
                if (loginNotification.hasErrors()) {
                    JOptionPane.showMessageDialog(loginView.getContentPane(), loginNotification.getFormattedErrors());
                } else {
                    JOptionPane.showMessageDialog(loginView.getContentPane(), "Login successful!");
                    Notification<Controller> nextController = getNextController("");
                    if (nextController != null) {
                        if (!nextController.hasErrors()) {
                            nextController.getResult().setVisibility(true);
                        }
                        else {
                            JOptionPane.showMessageDialog(loginView.getContentPane(), nextController.getFormattedErrors());
                        }
                    }
                }
            }
        }
    }

    private class RegisterButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            Notification<Boolean> registerNotification = authenticationService.register(username, password);
            if (registerNotification.hasErrors()) {
                JOptionPane.showMessageDialog(loginView.getContentPane(), registerNotification.getFormattedErrors());
            } else {
                if (!registerNotification.getResult()) {
                    JOptionPane.showMessageDialog(loginView.getContentPane(), "Registration not successful, please try again later.");
                } else {
                    JOptionPane.showMessageDialog(loginView.getContentPane(), "Registration successful!");
                }
            }
        }
    }



}
