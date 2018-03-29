package controller;

import model.Client;
import model.builder.ClientBuilder;
import model.validation.Notification;
import service.client.ClientService;
import view.AddClientView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddClientController {

    private final AddClientView addClientView;
    private final ClientService clientService;

    public AddClientController(AddClientView addClientView, ClientService clientService) {
        this.addClientView = addClientView;
        this.clientService = clientService;
        this.addClientView.setSaveChangesButtonListener(new SaveChangesListener());
    }

    private class SaveChangesListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            String name = addClientView.getName();
            String idCardNo = addClientView.getIdCardNo();
            String personalNumericalCode = addClientView.getPersonalNumericalCode();
            String address = addClientView.getAddress();
            Client client = new ClientBuilder()
                                .setName(name)
                                .setId_card_no(idCardNo)
                                .setPersonal_numerical_code(personalNumericalCode)
                                .setAddress(address)
                                .build();

            Notification<Boolean> addClientNotification = clientService.save(client);
            if (addClientNotification.hasErrors()) {
                JOptionPane.showMessageDialog(addClientView.getContentPane(), addClientNotification.getFormattedErrors());
            } else {
                if (!addClientNotification.getResult()) {
                    JOptionPane.showMessageDialog(addClientView.getContentPane(), "Adding client not successful, please try again later.");
                } else {
                    JOptionPane.showMessageDialog(addClientView.getContentPane(), "Client was added!");
                }
            }

        }
    }

}
