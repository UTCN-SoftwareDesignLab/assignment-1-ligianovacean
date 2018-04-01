package controller;

import model.validation.Notification;
import service.user.UserService;
import view.CreateEmployeeView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateEmployeeController implements Controller {

    private final CreateEmployeeView createEmployeeView;
    private final UserService userService;

    public CreateEmployeeController(CreateEmployeeView createEmployeeView, UserService userService) {
        this.createEmployeeView = createEmployeeView;
        this.userService = userService;
        this.createEmployeeView.setCreateButtonActionListener(new CreateListener());
    }

    @Override
    public Notification<Controller> getNextController(String selection) {
        return null;
    }

    @Override
    public void setVisibility(Boolean bool) {
        this.createEmployeeView.setVisibility(bool);
    }

    private class CreateListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = createEmployeeView.getUsername();
            String password = createEmployeeView.getPassword();

            Notification<Boolean> createEmployeeNotification = userService.createEmployee(username, password);
            if (createEmployeeNotification.hasErrors()) {
                JOptionPane.showMessageDialog(createEmployeeView.getContentPane(), createEmployeeNotification.getFormattedErrors());
            } else {
                if (!createEmployeeNotification.getResult()) {
                    JOptionPane.showMessageDialog(createEmployeeView.getContentPane(), "Registration not successful, please try again later.");
                } else {
                    JOptionPane.showMessageDialog(createEmployeeView.getContentPane(), "Registration successful!");
                }
            }
        }
    }


}
