package controller;

import database.Constants;
import model.Client;
import model.builder.ClientBuilder;
import model.validation.Notification;
import repository.EntityNotFoundException;
import service.client.ClientService;
import view.RUDView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class RUDClientController implements Controller{

    private final RUDView viewClientView;
    private final ClientService clientService;
    private final TableProcessing tableProcessing;

    public RUDClientController(RUDView viewClientView, ClientService clientService, TableProcessing tableProcessing) {
        this.tableProcessing = tableProcessing;
        this.clientService = clientService;
        this.viewClientView = viewClientView;
        JTable initialTable = tableProcessing.generateTable(clientService.findAllClients(), Constants.Columns.CLIENT_TABLE_COLUMNS);
        viewClientView.loadTable(initialTable);
        this.viewClientView.setBtnViewListener(new ViewListener());
        this.viewClientView.setBtnUpdateListener(new UpdateListener());
        this.viewClientView.setBtnDeleteListener(new DeleteListener());
    }

    @Override
    public Notification<Controller> getNextController(String selection) {
        return null;
    }

    @Override
    public void setVisibility(Boolean bool) {
        viewClientView.setVisibility(bool);
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
            List<Object> items = viewClientView.getSelectedRow();
            Client client = new ClientBuilder()
                                .setId(new Long((String)items.get(0)))
                                .setId_card_no((String)items.get(2))
                                .setName((String)items.get(1))
                                .setPersonal_numerical_code((String)items.get(3))
                                .setAddress((String)items.get(4))
                                .build();
            Notification<Boolean> updateClientNotification = clientService.updateClient(client);
            if (updateClientNotification.hasErrors()) {
                JOptionPane.showMessageDialog(viewClientView.getContentPane(), updateClientNotification.getFormattedErrors());
            } else {
                List<Client> clients = clientService.findAllClients();
//                List<Client> newClients = new ArrayList<>();
//                for (Client cl : clients) {
//                    if (cl.getId().longValue() == client.getId().longValue()) {
//                        newClients.add(client);
//                    }
//                    else
//                        newClients.add(cl);
//                }
//                for(Client c: newClients) {
//                    System.out.println(c.getName());
//                }
                viewClientView.loadTable(tableProcessing.generateTable(clients, Constants.Columns.CLIENT_TABLE_COLUMNS));
            }
        }
    }

    private class DeleteListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

}
