package controller;

import database.Constants;
import model.Account;
import model.validation.Notification;
import repository.EntityNotFoundException;
import service.account.AccountService;
import service.client.ClientService;
import service.user.UserActivityService;
import view.TransferView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static database.Constants.Roles.EMPLOYEE;

public class TranferController extends Controller{

    private final TransferView transferView;
    private final AccountService accountService;
    private final ClientService clientService;
    private final TableProcessing tableProcessing;

    public TranferController(TransferView transferView, AccountService accountService, ClientService clientService,
                             TableProcessing tableProcessing, UserActivityService activityService) {
        super(activityService);
        this.transferView = transferView;
        this.accountService = accountService;
        this.clientService = clientService;
        this.tableProcessing = tableProcessing;
        this.transferView.setBtnViewClientAccounts(new ViewClientsListener());
        this.transferView.setBtnTransferActionListener(new TransferListener());
    }

    @Override
    public void setVisibility(Boolean bool) {
        transferView.setVisible(bool);
        this.transferView.loadClientsTable(tableProcessing.generateTable(clientService.findAllClients(), Constants.Columns.CLIENT_TABLE_COLUMNS));
        this.transferView.loadAccountsTable(tableProcessing.generateTable(new ArrayList<>(), Constants.Columns.ACCOUNT_TABLE_COLUMNS));
    }


    private class ViewClientsListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            List<Object> items = transferView.getSelectedClient();
            Long clientId = Long.parseLong(items.get(0).toString());
            List<Account> clientAccounts = accountService.findAccountsByClientId(clientId);
            if (clientAccounts.size() == 0) {
                transferView.showMessage("The chosen client has no accounts. Please choose another client!");
            } else {
                transferView.loadAccountsTable(tableProcessing.generateTable(clientAccounts, Constants.Columns.ACCOUNT_TABLE_COLUMNS));
            }
        }
    }


    private class TransferListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            List<Object> accountItems = transferView.getSelectedAccount();
            Long firstAccountId = Long.parseLong(accountItems.get(0).toString());
            Long secondAccountId = Long.parseLong(transferView.getAccount2().toString());

            if (firstAccountId.longValue() == secondAccountId.longValue()) {
                transferView.showMessage( "The chosen accounts must be different");
                return;
            }

            Double sum = Double.parseDouble(transferView.getSum());
            Notification<Boolean> transferNotification;
            try {
                transferNotification = accountService.transferBetweenAccounts(firstAccountId, secondAccountId, sum);
                if (transferNotification.hasErrors()) {
                    transferView.showMessage(transferNotification.getFormattedErrors());
                } else {
                    transferView.showMessage("Transfer successful");
                    registerActivity(getLoggedInUser(), new Date(), "Transfered " + sum + " euros from account " + firstAccountId + " to account " + secondAccountId, EMPLOYEE);
                    List<Account> accounts = accountService.findAccountsByClientId(Long.parseLong(transferView.getSelectedClient().get(0).toString()));
                    transferView.loadAccountsTable(tableProcessing.generateTable(accounts, Constants.Columns.ACCOUNT_TABLE_COLUMNS));
                }
            } catch (EntityNotFoundException exc) {
                exc.printStackTrace();
            }
        }
    }

}
