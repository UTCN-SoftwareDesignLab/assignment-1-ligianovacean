package controller;

import model.validation.Notification;
import view.AdministratorView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class AdminController implements Controller{

    private final AdministratorView adminView;
    private final HashMap<String, Controller> controllers;

    public AdminController(AdministratorView adminView, HashMap<String, Controller> controllers) {
        this.adminView = adminView;
        this.controllers = controllers;
        adminView.setConfirmActionListener(new ConfirmListener());
    }

    private class ConfirmListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String selection = adminView.getSelection();
            Notification<Controller> adminNotification;
            adminNotification = getNextController(selection);
            if (adminNotification != null) {
                if (adminNotification.hasErrors()) {
                    JOptionPane.showMessageDialog(adminView.getContentPane(), adminNotification.getFormattedErrors());
                } else {
                    adminNotification.getResult().setVisibility(true);
                }
            }
        }
    }

    public Notification<Controller> getNextController(String selection) {
        Notification<Controller> controllerNotification = new Notification<>();
        if (selection.equals(""))
            controllerNotification.addError("You have made no choice!\n");
        else {
            controllerNotification.setResult(controllers.get(selection));
        }
        return controllerNotification;
    }

    @Override
    public void setVisibility(Boolean bool) {
        adminView.setVisibility(bool);
    }
}
