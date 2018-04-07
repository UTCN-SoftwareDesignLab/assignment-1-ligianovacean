package controller;

import database.Constants;
import model.Account;
import model.Bill;
import model.builder.BillBuilder;
import model.validation.Notification;
import service.account.AccountService;
import service.bill.payment.BillPaymentService;
import service.client.ClientService;
import service.user.UserActivityService;
import view.ProcessUtilityBillsView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static database.Constants.Roles.EMPLOYEE;

public class ProcessUtilityBillsController extends Controller{

    private final ProcessUtilityBillsView processView;
    private final ClientService clientService;
    private final AccountService accountService;
    private final BillPaymentService billPaymentService;
    private final TableProcessing tableProcessing;
    private Long clientId;

    public ProcessUtilityBillsController(ProcessUtilityBillsView processView, BillPaymentService billPaymentService,
                                         ClientService clientService, AccountService accountService,
                                         TableProcessing tableProcessing, UserActivityService activityService) {
        super(activityService);
        this.clientId = new Long(-1);
        this.processView = processView;
        this.billPaymentService = billPaymentService;
        this.clientService = clientService;
        this.accountService = accountService;
        this.tableProcessing = tableProcessing;
        this.processView.setBtnProcessActionListener(new ProcessListener());
        this.processView.setBtnViewAccountsActionListener(new ClientAccountsListener());
    }

    @Override
    public void setVisibility(Boolean bool) {
        processView.setVisible(bool);
        this.processView.loadClientsTable(tableProcessing.generateTable(clientService.findAllClients(), Constants.Columns.CLIENT_TABLE_COLUMNS));
        this.processView.loadAccountsTable(tableProcessing.generateTable(new ArrayList<>(), Constants.Columns.ACCOUNT_TABLE_COLUMNS));
    }

    private class ClientAccountsListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            List<Object> items = processView.getSelectedClient();
            clientId = Long.parseLong(items.get(0).toString());

            List<Account> clientAccounts = accountService.findAccountsByClientId(clientId);
            if (clientAccounts.size() == 0) {
                processView.showMessage("The chosen client has no accounts. Please choose another client!");
            } else {
                processView.loadAccountsTable(tableProcessing.generateTable(clientAccounts, Constants.Columns.ACCOUNT_TABLE_COLUMNS));
            }
        }
    }

    private class ProcessListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            List<Object> accountItems = processView.getSelectedAccount();
            if (accountItems == null) {
                processView.showMessage("Please choose an account!");
                return;
            }

            Long accountId = Long.parseLong(accountItems.get(0).toString());
            Double sum = Double.parseDouble(processView.getSum());
            String identifier = processView.getIdentifier();

            Bill bill = new BillBuilder()
                    .setIdClient(clientId)
                    .setIdentifier(identifier)
                    .setSum(sum)
                    .build();
            Notification<Boolean> payBill = billPaymentService.payBill(bill, accountId, sum);

            if (payBill.hasErrors()) {
                processView.showMessage(payBill.getFormattedErrors());
            } else{
                processView.showMessage("Payment successful!");
                registerActivity(getLoggedInUser(), new Date(), "Processed bill for client " + clientId + ", sum: " + sum, EMPLOYEE);
            }
            List<Account> accounts = accountService.findAccountsByClientId(clientId);
            processView.loadAccountsTable(tableProcessing.generateTable(accounts, Constants.Columns.ACCOUNT_TABLE_COLUMNS));
        }
    }
}
