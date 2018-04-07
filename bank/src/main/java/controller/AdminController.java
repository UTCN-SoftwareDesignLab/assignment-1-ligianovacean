package controller;

import model.validation.Notification;
import service.user.UserActivityService;
import view.AdministratorView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.HashMap;

public class AdminController extends Controller{

    private final AdministratorView adminView;
    private final HashMap<String, Controller> controllers;

    public AdminController(AdministratorView adminView, HashMap<String, Controller> controllers, UserActivityService activityService) {
        super(activityService);
        this.adminView = adminView;
        this.controllers = controllers;
        adminView.setConfirmActionListener(new ConfirmListener());
    }

    private class ConfirmListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String selection = adminView.getSelection();
            nextController(selection);
        }
    }

    private void nextController(String selection) {
        if (selection.equals(""))
             adminView.showMessage("Please choose an operation!");
        else {
             Controller controller = controllers.get(selection);
             controller.setVisibility(true);
             controller.setLoggedInUser(getLoggedInUser());
        }
    }

    @Override
    public void setVisibility(Boolean bool) {
        adminView.setVisible(bool);
    }
}
