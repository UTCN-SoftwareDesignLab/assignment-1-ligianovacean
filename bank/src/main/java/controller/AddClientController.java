package controller;

import model.Client;
import model.builder.ClientBuilder;
import model.validation.Notification;
import service.client.ClientService;
import service.user.UserActivityService;
import view.AddClientView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import static database.Constants.Roles.EMPLOYEE;

public class AddClientController extends Controller {

    private final AddClientView addClientView;
    private final ClientService clientService;

    public AddClientController(AddClientView addClientView, ClientService clientService, UserActivityService activityService) {
        super(activityService);
        this.addClientView = addClientView;
        this.clientService = clientService;
        this.addClientView.setSaveChangesButtonListener(new SaveChangesListener());
    }

    @Override
    public void setVisibility(Boolean bool) {
        addClientView.setVisible(bool);
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
                addClientView.showMessage(addClientNotification.getFormattedErrors());
            } else {
                if (!addClientNotification.getResult()) {
                    addClientView.showMessage("Operation not successful, please try again later");
                } else {
                    addClientView.showMessage("Operation successful, client added!");
                    registerActivity(getLoggedInUser(), new Date(), "Added client " + name, EMPLOYEE);
                }
            }

        }
    }

}
