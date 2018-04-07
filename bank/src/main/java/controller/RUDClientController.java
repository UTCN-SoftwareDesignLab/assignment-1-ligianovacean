package controller;

import database.Constants;
import model.Client;
import model.builder.ClientBuilder;
import model.validation.Notification;
import repository.EntityNotFoundException;
import service.client.ClientService;
import service.user.UserActivityService;
import view.RUDView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static database.Constants.Roles.EMPLOYEE;

public class RUDClientController extends Controller{

    private final RUDView viewClientView;
    private final ClientService clientService;
    private final TableProcessing tableProcessing;

    public RUDClientController(RUDView viewClientView, ClientService clientService,
                               TableProcessing tableProcessing, UserActivityService activityService) {
        super(activityService);
        this.tableProcessing = tableProcessing;
        this.clientService = clientService;
        this.viewClientView = viewClientView;
        this.viewClientView.resetDelete();
        this.viewClientView.setBtnViewListener(new ViewListener());
        this.viewClientView.setBtnUpdateListener(new UpdateListener());
    }

    @Override
    public void setVisibility(Boolean bool) {
        viewClientView.setVisible(bool);
        JTable initialTable = tableProcessing.generateTable(clientService.findAllClients(), Constants.Columns.CLIENT_TABLE_COLUMNS);
        viewClientView.loadTable(initialTable);
    }

    private class ViewListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            Long id = Long.parseLong(viewClientView.getUniqueIdentifier());
            try {
                Notification<Client> viewClientNotification = clientService.viewClient(id);
                if (viewClientNotification.hasErrors()) {
                    viewClientView.showMessage( viewClientNotification.getFormattedErrors());
                } else {
                    String[] columnNames = Constants.Columns.CLIENT_TABLE_COLUMNS;
                    Client client = viewClientNotification.getResult();
                    JTable table = tableProcessing.generateTable(Collections.singletonList(client), columnNames);
                    viewClientView.loadTable(table);
                    registerActivity(getLoggedInUser(), new Date(), "Viewed client " + id, EMPLOYEE);
                }
            } catch (EntityNotFoundException exc) {
                viewClientView.showMessage( "Viewing client not successful, please try again later.");
            }
        }
    }


    private class UpdateListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            List<Object> items = viewClientView.getSelectedRow();
            Client client = new ClientBuilder()
                                .setId(Long.parseLong(items.get(0).toString()))
                                .setId_card_no(items.get(2).toString())
                                .setName(items.get(1).toString())
                                .setPersonal_numerical_code(items.get(3).toString())
                                .setAddress(items.get(4).toString())
                                .build();
            Notification<Boolean> updateClientNotification = clientService.updateClient(client);
            if (updateClientNotification.hasErrors()) {
                JOptionPane.showMessageDialog(viewClientView.getContentPane(), updateClientNotification.getFormattedErrors());
            } else {
                List<Client> clients = clientService.findAllClients();
                registerActivity(getLoggedInUser(), new Date(), "Updated client " + Long.parseLong(items.get(0).toString()), EMPLOYEE);
                viewClientView.loadTable(tableProcessing.generateTable(clients, Constants.Columns.CLIENT_TABLE_COLUMNS));
            }
        }
    }

}
