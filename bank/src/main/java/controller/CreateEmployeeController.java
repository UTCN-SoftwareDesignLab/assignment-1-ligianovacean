package controller;

import model.validation.Notification;
import service.user.UserActivityService;
import service.user.UserService;
import view.CreateEmployeeView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import static database.Constants.Roles.ADMINISTRATOR;

public class CreateEmployeeController extends Controller {

    private final CreateEmployeeView createEmployeeView;
    private final UserService userService;

    public CreateEmployeeController(CreateEmployeeView createEmployeeView, UserService userService,
                                    UserActivityService activityService) {
        super(activityService);
        this.createEmployeeView = createEmployeeView;
        this.userService = userService;
        this.createEmployeeView.setCreateButtonActionListener(new CreateListener());
    }

    @Override
    public void setVisibility(Boolean bool) {
        this.createEmployeeView.setVisible(bool);
    }

    private class CreateListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = createEmployeeView.getUsername();
            String password = createEmployeeView.getPassword();

            Notification<Boolean> createEmployeeNotification = userService.createEmployee(username, password);
            if (createEmployeeNotification.hasErrors()) {
                createEmployeeView.showMessage(createEmployeeNotification.getFormattedErrors());
            } else {
                if (!createEmployeeNotification.getResult()) {
                    createEmployeeView.showMessage("Registration not successful, please try again later.");
                } else {
                    registerActivity(getLoggedInUser(), new Date(), "Created employee with username " + username, ADMINISTRATOR);
                    createEmployeeView.showMessage("Registration successful!");
                }
            }
        }
    }


}
