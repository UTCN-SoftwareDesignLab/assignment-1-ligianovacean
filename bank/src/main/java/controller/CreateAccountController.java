package controller;

import database.Constants;
import model.Account;
import model.builder.AccountBuilder;
import model.validation.Notification;
import service.account.AccountService;
import service.client.ClientService;
import view.CreateAccountView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CreateAccountController implements Controller{

    private final CreateAccountView createAccountView;
    private final AccountService accountService;
    private final ClientService clientService;
    private final TableProcessing tableProcessing;

    public CreateAccountController(CreateAccountView createAccountView, AccountService accountService, ClientService clientService, TableProcessing tableProcessing) {
        this.createAccountView = createAccountView;
        this.accountService = accountService;
        this.tableProcessing = tableProcessing;
        this.clientService = clientService;
        this.createAccountView.loadTable(tableProcessing.generateTable(clientService.findAllClients(), Constants.Columns.ACCOUNT_TABLE_COLUMNS));
        this.createAccountView.setCreateButtonActionListener(new CreateListener());
    }

    @Override
    public Notification<Controller> getNextController(String selection) {
        return null;
    }

    @Override
    public void setVisibility(Boolean bool) {
        createAccountView.setVisibility(bool);
    }

    private class CreateListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String type = createAccountView.getAccountType();
            String date = createAccountView.getCreationDate();
            Date creationDate = processDate(date);
            Long clientId = createAccountView.getSelectedRow();
            Account account = new AccountBuilder()
                                .setType(type)
                                .setCreation_date(creationDate)
                                .setId_client(new Long(clientId))
                                .setSum(new Double(0))
                                .build();

            Notification<Boolean> createAccountNotification = accountService.save(account);
            if (createAccountNotification.hasErrors()) {
                JOptionPane.showMessageDialog(createAccountView.getContentPane(), createAccountNotification.getFormattedErrors());
            } else {
                if (!createAccountNotification.getResult()) {
                    JOptionPane.showMessageDialog(createAccountView.getContentPane(), "Adding account not successful, please try again later.");
                } else {
                    JOptionPane.showMessageDialog(createAccountView.getContentPane(), "Account was added!");
                }
            }
        }
    }

    private Date processDate(String date) {
        String[] dateComponents = date.split("-");
        int year = Integer.parseInt(dateComponents[0]);
        int month = 0;
        if (dateComponents[1].startsWith("0")) {
            month = Integer.parseInt(dateComponents[1].substring(1));
        }
        else {
            month = Integer.parseInt(dateComponents[1]);
        }
        int day = 0;
        if (dateComponents[2].startsWith("0")) {
            day = Integer.parseInt(dateComponents[2].substring(1));
        }
        else {
            day = Integer.parseInt(dateComponents[2]);
        }
        Calendar calendar = new GregorianCalendar(year, month, day);
        Date creationDate = calendar.getTime();
        return creationDate;
    }

}
