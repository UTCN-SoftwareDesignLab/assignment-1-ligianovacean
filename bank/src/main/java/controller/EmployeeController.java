package controller;

import model.validation.Notification;
import view.AddClientView;
import view.EmployeeView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;

public class EmployeeController implements Controller{

    private final EmployeeView employeeView;

//    private final AddClientController addClientController;
//    private final RUDClientController rudClientController;
//    private final CreateAccountController createAccountController;
//    private final RUDAccountController rudAccountController;
//    private final TranferController tranferController;
//    private final ProcessUtilityBillsController billsController;
    HashMap<String, Controller> controllers;

    public EmployeeController(EmployeeView employeeView, HashMap<String, Controller> controllers) {
        this.employeeView = employeeView;
        this.controllers = controllers;
        this.employeeView.setConfirmSelectionButtonListener(new ConfirmSelectionButtonListener());
    }

    private class ConfirmSelectionButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selection = employeeView.getSelection();

            Notification<Controller> employeeNotification;
            employeeNotification = getNextController(selection);
            if (employeeNotification != null) {
                if (employeeNotification.hasErrors()) {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), employeeNotification.getFormattedErrors());
                } else {
                    employeeNotification.getResult().setVisibility(true);
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
        employeeView.setVisibility(bool);
    }

}
