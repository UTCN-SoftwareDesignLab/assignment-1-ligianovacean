package controller;

import database.Constants;
import model.Account;
import model.validation.Notification;
import repository.EntityNotFoundException;
import service.account.AccountService;
import service.client.ClientService;
import view.TransferView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class TranferController implements Controller{

    private final TransferView transferView;
    private final AccountService accountService;
    private final ClientService clientService;
    private final TableProcessing tableProcessing;

    public TranferController(TransferView transferView, AccountService accountService, ClientService clientService, TableProcessing tableProcessing) {
        this.transferView = transferView;
        this.accountService = accountService;
        this.clientService = clientService;
        this.tableProcessing = tableProcessing;
        this.transferView.loadClientsTable(tableProcessing.generateTable(clientService.findAllClients(), Constants.Columns.CLIENT_TABLE_COLUMNS));
        this.transferView.loadAccountsTable(tableProcessing.generateTable(new ArrayList<>(), Constants.Columns.ACCOUNT_TABLE_COLUMNS));
        this.transferView.setBtnViewClientAccounts(new ViewClientsListener());
        this.transferView.setBtnTransferActionListener(new TransferListener());
    }

    @Override
    public Notification<Controller> getNextController(String selection) {
        return null;
    }

    @Override
    public void setVisibility(Boolean bool) {
        transferView.setVisibility(bool);
    }


    private class ViewClientsListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            List<Object> items = transferView.getSelectedClient();
            Long clientId = new Long((String)items.get(0));
            List<Account> clientAccounts = accountService.findAccountsByClientId(clientId);
            if (clientAccounts.size() == 0) {
                JOptionPane.showMessageDialog(transferView.getContentPane(), "The chosen client has no accounts. Please choose another client!");
            } else {
                transferView.loadAccountsTable(tableProcessing.generateTable(clientAccounts, Constants.Columns.ACCOUNT_TABLE_COLUMNS));
            }
        }
    }


    private class TransferListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            List<Object> accountItems = transferView.getSelectedAccount();
            Long firstAccountId = new Long((String)accountItems.get(0));
            Long secondAccountId = new Long((String)transferView.getAccount2());
            if (firstAccountId.longValue() == secondAccountId.longValue()) {
                JOptionPane.showMessageDialog(transferView.getContentPane(), "The chosen accounts must be different");
            } else {
                Double sum = new Double((String)transferView.getSum());
                Notification<Boolean> transferNotification = new Notification<>();
                try {
                    transferNotification = accountService.transferBetweenAccounts(firstAccountId, secondAccountId, sum);
                } catch (EntityNotFoundException exc) {
                    exc.printStackTrace();
                }

                if (transferNotification.hasErrors()) {
                    JOptionPane.showMessageDialog(transferView.getContentPane(), transferNotification.getFormattedErrors());
                } else {
                    JOptionPane.showMessageDialog(transferView.getContentPane(), "Transfer successful");
                    List<Account> accounts = accountService.findAccountsByClientId(new Long((String) transferView.getSelectedClient().get(0)));
                    transferView.loadAccountsTable(tableProcessing.generateTable(accounts, Constants.Columns.ACCOUNT_TABLE_COLUMNS));
                }
            }
        }
    }

}
