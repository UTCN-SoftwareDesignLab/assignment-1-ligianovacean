package controller;

import model.validation.Notification;
import view.EmployeeView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmployeeController {

//    private final EmployeeView employeeView;
//
//    public EmployeeController(EmployeeView employeeView) {
//        this.employeeView = employeeView;
//        employeeView.setConfirmSelectionButtonListener(new ConfirmSelectionButtonListener());
//    }
//
//    private class ConfirmSelectionButtonListener implements ActionListener {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            String selection = employeeView.getSelection();
//
//            Notification<controller.ControllerFactory> employeeNotification = null;
//            employeeNotification = getController(selection);
//            if (employeeNotification != null) {
//                if (employeeNotification.hasErrors()) {
//                    JOptionPane.showMessageDialog(employeeView.getContentPane(), employeeNotification.getFormattedErrors());
//                }
//            }
//        }
//    }
//
//    private Notification<controller.ControllerFactory> getController(String selection) {
//        Notification<controller.ControllerFactory> controllerNotification = new Notification<>();
//        if (selection.equals(""))
//            controllerNotification.addError("You have made no choice!\n");
//        else {
//            if (selection.equals("Add Client")) {
//                //controllerNotification.setResult(new AddClientController());
//                System.out.println("AddClient");
//            } else {
//                //controllerNotification.setResult(new UpdateClientController());
//                System.out.println("Update Client");
//            }
//        }
//        return controllerNotification;
//    }

}
