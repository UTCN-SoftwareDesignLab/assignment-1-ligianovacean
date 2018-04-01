package controller;

import database.Constants;
import model.User;
import model.builder.UserBuilder;
import model.validation.Notification;
import service.user.UserService;
import view.RUDView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RUDEmployeeController implements Controller{

    private final UserService userService;
    private final RUDView rudView;
    private final controller.TableProcessing tableProcessing;

    public RUDEmployeeController(RUDView rudView, UserService userService, controller.TableProcessing tableProcessing) {
        this.userService = userService;
        this.rudView = rudView;
        this.tableProcessing = tableProcessing;
        rudView.loadTable(tableProcessing.generateTable(userService.findAll(), Constants.Columns.EMPLOYEE_TABLE_COLUMNS));
        this.rudView.setBtnViewListener(new ViewListener());
        this.rudView.setBtnUpdateListener(new UpdateListener());
        this.rudView.setBtnDeleteListener(new DeleteListener());
    }

    @Override
    public Notification<Controller> getNextController(String selection) {
        return null;
    }

    @Override
    public void setVisibility(Boolean bool) {
        this.rudView.setVisibility(bool);
    }

    private class ViewListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = rudView.getUniqueIdentifier();

            JTable tbl;
            Notification<User> viewUserNotification = userService.viewEmployee(username);
            if (viewUserNotification.hasErrors()){
                JOptionPane.showMessageDialog(rudView.getContentPane(), viewUserNotification.getFormattedErrors());
                tbl = tableProcessing.generateTable(new ArrayList<User>(), Constants.Columns.EMPLOYEE_TABLE_COLUMNS);
            } else {
                User user = viewUserNotification.getResult();
                tbl = tableProcessing.generateTable(Collections.singletonList(user), Constants.Columns.EMPLOYEE_TABLE_COLUMNS);
            }
            rudView.loadTable(tbl);
        }
    }

    private class UpdateListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            List<Object> selection = rudView.getSelectedRow();
            Long id = new Long((String)selection.get(0));
            String username = (String)selection.get(1);

            Notification<Boolean> updateNotification = userService.updateUser(id, username);
            if (updateNotification.hasErrors()) {
                JOptionPane.showMessageDialog(rudView.getContentPane(), updateNotification.getFormattedErrors());
            } else {
                List<User> users = userService.findAll();
                rudView.loadTable(tableProcessing.generateTable(users, Constants.Columns.EMPLOYEE_TABLE_COLUMNS));
            }
        }
    }

    private class DeleteListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            List<Object> selection = rudView.getSelectedRow();
            User user = new UserBuilder()
                    .setId(new Long((String) selection.get(0)))
                    .build();
            if (!userService.deleteUser(user)) {
                JOptionPane.showMessageDialog(rudView.getContentPane(), "User could not be deleted. Please try again later!");
            } else {
                List<User> users = userService.findAll();
                rudView.loadTable(tableProcessing.generateTable(users, Constants.Columns.EMPLOYEE_TABLE_COLUMNS));
            }
        }
    }
}
