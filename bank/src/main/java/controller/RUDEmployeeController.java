package controller;

import database.Constants;
import model.User;
import model.builder.UserBuilder;
import model.validation.Notification;
import service.user.UserActivityService;
import service.user.UserService;
import view.RUDView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static database.Constants.Roles.ADMINISTRATOR;

public class RUDEmployeeController extends Controller{

    private final UserService userService;
    private final RUDView rudView;
    private final TableProcessing tableProcessing;

    public RUDEmployeeController(RUDView rudView, UserService userService,
                                 TableProcessing tableProcessing, UserActivityService activityService) {
        super(activityService);
        this.userService = userService;
        this.rudView = rudView;
        this.tableProcessing = tableProcessing;
        rudView.setIdentifierLabel();
        this.rudView.setBtnViewListener(new ViewListener());
        this.rudView.setBtnUpdateListener(new UpdateListener());
        this.rudView.setBtnDeleteListener(new DeleteListener());
    }

    @Override
    public void setVisibility(Boolean bool) {
        this.rudView.setVisible(bool);
        rudView.loadTable(tableProcessing.generateTable(userService.findAll(), Constants.Columns.EMPLOYEE_TABLE_COLUMNS));
    }

    private class ViewListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = rudView.getUniqueIdentifier();

            JTable tbl;
            Notification<User> viewUserNotification = userService.viewEmployee(username);
            if (viewUserNotification.hasErrors()){
                rudView.showMessage( viewUserNotification.getFormattedErrors());
                tbl = tableProcessing.generateTable(new ArrayList<User>(), Constants.Columns.EMPLOYEE_TABLE_COLUMNS);
            } else {
                User user = viewUserNotification.getResult();
                tbl = tableProcessing.generateTable(Collections.singletonList(user), Constants.Columns.EMPLOYEE_TABLE_COLUMNS);
                registerActivity(getLoggedInUser(), new Date(), "Viewed employee " + username, ADMINISTRATOR);
            }
            rudView.loadTable(tbl);
        }
    }

    private class UpdateListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            List<Object> selection = rudView.getSelectedRow();
            Long id = Long.parseLong(selection.get(0).toString());
            String username = selection.get(1).toString();

            Notification<Boolean> updateNotification = userService.updateUser(id, username);
            if (updateNotification.hasErrors()) {
                rudView.showMessage( updateNotification.getFormattedErrors());
            } else {
                List<User> users = userService.findAll();
                rudView.loadTable(tableProcessing.generateTable(users, Constants.Columns.EMPLOYEE_TABLE_COLUMNS));
                registerActivity(getLoggedInUser(), new Date(), "Updated employee " + id, ADMINISTRATOR);
            }
        }
    }

    private class DeleteListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            List<Object> selection = rudView.getSelectedRow();
            User user = new UserBuilder()
                    .setId(Long.parseLong(selection.get(0).toString()))
                    .build();
            if (!userService.deleteUser(user)) {
                rudView.showMessage("User could not be deleted. Please try again!");
            } else {
                List<User> users = userService.findAll();
                rudView.loadTable(tableProcessing.generateTable(users, Constants.Columns.EMPLOYEE_TABLE_COLUMNS));
                registerActivity(getLoggedInUser(), new Date(), "Deleted employee " + Long.parseLong(selection.get(0).toString()), ADMINISTRATOR);
            }
        }
    }
}
