package controller;

import database.Constants;
import model.Account;
import model.builder.AccountBuilder;
import model.validation.Notification;
import repository.EntityNotFoundException;
import service.account.AccountService;
import service.user.UserActivityService;
import view.RUDView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static database.Constants.Roles.EMPLOYEE;

public class RUDAccountController extends Controller{

    private final RUDView accountView;
    private final AccountService accountService;
    private final TableProcessing tableProcessing;

    public RUDAccountController(RUDView accountView, AccountService accountService, TableProcessing tableProcessing, UserActivityService activityService) {
        super(activityService);
        this.accountView = accountView;
        this.accountService = accountService;
        this.tableProcessing = tableProcessing;
        this.accountView.setBtnViewListener(new ViewListener());
        this.accountView.setBtnUpdateListener(new UpdateListener());
        this.accountView.setBtnDeleteListener(new DeleteListener());
    }

    @Override
    public void setVisibility(Boolean bool) {
        accountView.setVisible(bool);
        accountView.loadTable(tableProcessing.generateTable(accountService.findAllAccounts(), Constants.Columns.ACCOUNT_TABLE_COLUMNS));
    }

    private class ViewListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Long id = Long.parseLong(accountView.getUniqueIdentifier());
            try {
                Notification<Account> viewAccountNotification = accountService.viewAccount(id);
                if (viewAccountNotification.hasErrors()) {
                    accountView.showMessage(viewAccountNotification.getFormattedErrors());
                } else {
                    Account account = viewAccountNotification.getResult();
                    JTable table = tableProcessing.generateTable(Collections.singletonList(account), Constants.Columns.ACCOUNT_TABLE_COLUMNS);
                    accountView.loadTable(table);
                    registerActivity(getLoggedInUser(), new Date(), "Viewed account " + id, EMPLOYEE);
                }
            } catch (EntityNotFoundException exc) {
                accountView.showMessage("Viewing account not successful, please try again later.");
            }
        }
    }

    private class UpdateListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            List<Object> items = accountView.getSelectedRow();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date;
            try {
                date  = formatter.parse(items.get(3).toString());

                Account account = new AccountBuilder()
                        .setId(Long.parseLong(items.get(0).toString()))
                        .setType(items.get(1).toString())
                        .setSum(Double.parseDouble(items.get(2).toString()))
                        .setCreation_date(date)
                        .setId_client(Long.parseLong(items.get(4).toString()))
                        .build();

                Notification<Boolean> updateAccountNotification = accountService.updateAccount(account);
                if (updateAccountNotification.hasErrors()) {
                    accountView.showMessage(updateAccountNotification.getFormattedErrors());
                } else {
                    List<Account> accounts = accountService.findAllAccounts();
                    accountView.loadTable(tableProcessing.generateTable(accounts, Constants.Columns.ACCOUNT_TABLE_COLUMNS));
                    registerActivity(getLoggedInUser(), new Date(), "Updated account " + Long.parseLong(items.get(0).toString()), EMPLOYEE);
                }
            } catch (ParseException exc) {
                accountView.showMessage("Date is not in the proper format.");
            }
        }
    }

    private class DeleteListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            List<Object> items = accountView.getSelectedRow();
            Account account = new AccountBuilder()
                                .setId(Long.parseLong(items.get(0).toString()))
                                .build();
            if (!accountService.deleteAccount(account)){
                accountView.showMessage("Account could not be deleted. Please try again later!");
            } else {
                List<Account> accounts = accountService.findAllAccounts();
                accountView.loadTable(tableProcessing.generateTable(accounts, Constants.Columns.ACCOUNT_TABLE_COLUMNS));
                registerActivity(getLoggedInUser(), new Date(), "Deleted sccount " + Long.parseLong(items.get(0).toString()), EMPLOYEE);
            }
        }
    }


}
