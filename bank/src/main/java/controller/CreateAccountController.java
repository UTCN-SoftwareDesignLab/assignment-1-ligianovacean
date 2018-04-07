package controller;

import database.Constants;
import model.Account;
import model.builder.AccountBuilder;
import model.validation.Notification;
import service.account.AccountService;
import service.client.ClientService;
import service.user.UserActivityService;
import view.CreateAccountView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import static database.Constants.Roles.EMPLOYEE;

public class CreateAccountController extends Controller{

    private final CreateAccountView createAccountView;
    private final AccountService accountService;
    private final ClientService clientService;
    private final TableProcessing tableProcessing;

    public CreateAccountController(CreateAccountView createAccountView, AccountService accountService, ClientService clientService, TableProcessing tableProcessing,
                                   UserActivityService activityService) {
        super(activityService);
        this.createAccountView = createAccountView;
        this.accountService = accountService;
        this.tableProcessing = tableProcessing;
        this.clientService = clientService;
        this.createAccountView.setCreateButtonActionListener(new CreateListener());
    }

    @Override
    public void setVisibility(Boolean bool) {
        createAccountView.setVisible(bool);
        this.createAccountView.loadTable(tableProcessing.generateTable(clientService.findAllClients(), Constants.Columns.ACCOUNT_TABLE_COLUMNS));
    }

    private class CreateListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Date creationDate = (new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).parse(createAccountView.getCreationDate()));
                Long clientId = createAccountView.getSelectedRow();
                Account account = new AccountBuilder()
                        .setType(createAccountView.getAccountType())
                        .setCreation_date(creationDate)
                        .setId_client(clientId)
                        .setSum(new Double(0))
                        .build();

                Notification<Boolean> createAccountNotification = accountService.save(account);
                if (createAccountNotification.hasErrors()) {
                    createAccountView.showMessage(createAccountNotification.getFormattedErrors());
                } else {
                    if (!createAccountNotification.getResult()) {
                        createAccountView.showMessage("Adding account not successful, please try again later.");
                    } else {
                        registerActivity(getLoggedInUser(), account.getCreation_date(), "Created " + account.getType() + " for client" + account.getId_client(), EMPLOYEE);
                        createAccountView.showMessage("Account was added!");
                    }
                }
            } catch (ParseException exc) {
                createAccountView.showMessage("Incorrect date format! \n Proper date format example: March 3, 2004");
            }
        }
    }

}
