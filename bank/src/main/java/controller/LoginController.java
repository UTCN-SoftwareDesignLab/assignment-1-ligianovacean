package controller;

import model.Role;
import model.User;
import model.validation.Notification;
import repository.user.AuthenticationException;
import service.security.AuthenticationService;
import service.user.UserActivityService;
import service.user.UserService;
import view.LoginView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class LoginController extends Controller{

    private final LoginView loginView;
    private final AuthenticationService authenticationService;
    private final UserService userService;

    private final HashMap<String, Controller> controllersHashMap;

    public LoginController(LoginView loginView, AuthenticationService authenticationService, UserService userService,
                           HashMap<String, Controller> controllersHashMap, UserActivityService activityService) {
        super(activityService);
        this.loginView = loginView;
        this.authenticationService = authenticationService;
        this.userService = userService;
        this.controllersHashMap = controllersHashMap;
        loginView.setLoginButtonListener(new LoginButtonListener());
        loginView.setRegisterButtonListener(new RegisterButtonListener());
        loginView.setLogoutButtonListener(new LogoutButtonListener());
        setLoggedInUser(-1L);
    }

    @Override
    public void setVisibility(Boolean bool) {
        loginView.setVisible(bool);
    }

    private class LoginButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (getLoggedInUser() != -1) {
                loginView.showMessage("Only one user can sign in at a time!");
                return;
            }

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
                    loginView.showMessage(loginNotification.getFormattedErrors());
                } else {
                    JOptionPane.showMessageDialog(loginView.getContentPane(), "Login successful!");
                    User user = userService.viewEmployee(username).getResult();
                    setLoggedInUser(user.getId());
                    nextController(userService.getRoles(user));
                    for (Role role : userService.getRoles(user)) {
                        registerActivity(getLoggedInUser(), new Date(), "Log in", role.getRole());
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

            Notification<Boolean> registerNotification = authenticationService.register(username, password, e.getActionCommand());
            if (registerNotification.hasErrors()) {
                loginView.showMessage(registerNotification.getFormattedErrors());
            } else {
                if (!registerNotification.getResult()) {
                    loginView.showMessage("Registration not successful, please try again later.");
                } else {
                    loginView.showMessage("Registration successful!");
                }
            }
        }
    }

    private class LogoutButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (getLoggedInUser() == -1L) {
                loginView.showMessage("You must firstly log in!");
            } else {
                setLoggedInUser(-1L);
                loginView.showMessage("Logout successful!");
            }
        }
    }

    private void nextController(List<Role> roles) {
        for (Role role : roles) {
            controllersHashMap.get(role.getRole()).setVisibility(true);
            controllersHashMap.get(role.getRole()).setLoggedInUser(getLoggedInUser());
        }
    }

}
