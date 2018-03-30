package controller;

import database.Constants;
import model.Account;
import model.Bill;
import model.builder.BillBuilder;
import model.validation.Notification;
import service.account.AccountService;
import service.bill.payment.BillPaymentService;
import service.client.ClientService;
import view.ProcessUtilityBillsView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ProcessUtilityBillsController implements Controller{

    private final ProcessUtilityBillsView processView;
    private final ClientService clientService;
    private final AccountService accountService;
    private final BillPaymentService billPaymentService;
    private final TableProcessing tableProcessing;
    private Long clientId;

    public ProcessUtilityBillsController(ProcessUtilityBillsView processView, BillPaymentService billPaymentService,
                                         ClientService clientService, AccountService accountService,
                                         TableProcessing tableProcessing) {
        this.clientId = new Long(-1);
        this.processView = processView;
        this.billPaymentService = billPaymentService;
        this.clientService = clientService;
        this.accountService = accountService;
        this.tableProcessing = tableProcessing;
        this.processView.loadClientsTable(tableProcessing.generateTable(clientService.findAllClients(), Constants.Columns.CLIENT_TABLE_COLUMNS));
        this.processView.loadAccountsTable(tableProcessing.generateTable(new ArrayList<>(), Constants.Columns.ACCOUNT_TABLE_COLUMNS));
        this.processView.setBtnProcessActionListener(new ProcessListener());
        this.processView.setBtnViewAccountsActionListener(new ClientAccountsListener());
    }

    @Override
    public Notification<Controller> getNextController(String selection) {
        return null;
    }

    @Override
    public void setVisibility(Boolean bool) {
        processView.setVisibility(bool);
    }

    private class ClientAccountsListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            List<Object> items = processView.getSelectedClient();
            clientId = new Long((String)items.get(0));
            List<Account> clientAccounts = accountService.findAccountsByClientId(clientId);
            if (clientAccounts.size() == 0) {
                JOptionPane.showMessageDialog(processView.getContentPane(), "The chosen client has no accounts. Please choose another client!");
            } else {
                processView.loadAccountsTable(tableProcessing.generateTable(clientAccounts, Constants.Columns.ACCOUNT_TABLE_COLUMNS));
            }
        }
    }

    private class ProcessListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            List<Object> accountItems = processView.getSelectedAccount();
            Long accountId = new Long((String)accountItems.get(0));
            Double sum = new Double(processView.getSum());
            String identifier = processView.getIdentifier();
            List<Object> items = processView.getSelectedClient();

            Bill bill = new BillBuilder()
                    .setIdClient(clientId)
                    .setIdentifier(identifier)
                    .setSum(sum)
                    .build();

            Notification<Boolean> payBill = billPaymentService.payBill(bill, accountId, sum);
            if (payBill.hasErrors()) {
                JOptionPane.showMessageDialog(processView.getContentPane(), payBill.getFormattedErrors());
            } else{
                JOptionPane.showMessageDialog(processView.getContentPane(), "Payment successful!");
            }
            List<Account> accounts = accountService.findAccountsByClientId(clientId);
            processView.loadAccountsTable(tableProcessing.generateTable(accounts, Constants.Columns.ACCOUNT_TABLE_COLUMNS));
        }
    }
}
