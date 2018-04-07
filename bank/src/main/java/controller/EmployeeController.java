package controller;

import model.validation.Notification;
import service.user.UserActivityService;
import view.AddClientView;
import view.EmployeeView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;

public class EmployeeController extends Controller{

    private final EmployeeView employeeView;

    HashMap<String, Controller> controllers;

    public EmployeeController(EmployeeView employeeView, HashMap<String, Controller> controllers, UserActivityService activityService) {
        super(activityService);
        this.employeeView = employeeView;
        this.controllers = controllers;
        this.employeeView.setConfirmSelectionButtonListener(new ConfirmSelectionButtonListener());
    }

    private class ConfirmSelectionButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selection = employeeView.getSelection();
            nextController(selection);
        }
    }

    private void nextController(String selection) {
        if (selection.equals(""))
            employeeView.showMessage("Please choose an operation!");
        else {
            Controller controller = controllers.get(selection);
            controller.setVisibility(true);
            controller.setLoggedInUser(getLoggedInUser());
        }
    }

    @Override
    public void setVisibility(Boolean bool) {
        employeeView.setVisible(bool);
    }

}
