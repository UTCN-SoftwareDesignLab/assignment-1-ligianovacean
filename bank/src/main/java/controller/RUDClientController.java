package controller;

import database.Constants;
import model.Client;
import model.builder.ClientBuilder;
import model.validation.Notification;
import repository.EntityNotFoundException;
import service.client.ClientService;
import view.AddClientView;
import view.RUDView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class RUDClientController {

    private final RUDView viewClientView;
    private final ClientService clientService;
    private final TableProcessing tableProcessing;

    public RUDClientController(RUDView viewClientView, ClientService clientService, TableProcessing tableProcessing) {
        this.viewClientView = viewClientView;
        this.clientService = clientService;
        this.tableProcessing = tableProcessing;
        this.viewClientView.setBtnViewListener(new ViewListener());
        this.viewClientView.setBtnUpdateListener(new UpdateListener());
        this.viewClientView.setBtnDeleteListener(new DeleteListener());
    }

    private class ViewListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            Long id = Long.parseLong(viewClientView.getUniqueIdentifier());
            try {
                Notification<Client> viewClientNotification = clientService.viewClient(id);
                if (viewClientNotification.hasErrors()) {
                    JOptionPane.showMessageDialog(viewClientView.getContentPane(), viewClientNotification.getFormattedErrors());
                } else {
                    String[] columnNames = Constants.Columns.CLIENT_TABLE_COLUMNS;
                    Client client = viewClientNotification.getResult();
                    //Object[][] data = { {client.getId(), client.getName(), client.getId_card_no(), client.getPersonal_numerical_code(), client.getAddress()}};
                    List<Client> clients = new ArrayList<>();
                    clients.add(client);
                    JTable table = tableProcessing.generateTable(clients, columnNames);
                    viewClientView.loadTable(table);
                }
            } catch (EntityNotFoundException exc) {
                JOptionPane.showMessageDialog(viewClientView.getContentPane(), "Viewing client not successful, please try again later.");
            }

        }
    }


    private class UpdateListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class DeleteListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

}
