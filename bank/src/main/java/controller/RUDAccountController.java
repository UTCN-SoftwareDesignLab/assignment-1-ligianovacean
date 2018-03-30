package controller;

import database.Constants;
import model.Account;
import model.builder.AccountBuilder;
import model.validation.Notification;
import repository.EntityNotFoundException;
import service.account.AccountService;
import view.RUDView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RUDAccountController implements Controller{

    private final RUDView accountView;
    private final AccountService accountService;
    private final TableProcessing tableProcessing;

    public RUDAccountController(RUDView accountView, AccountService accountService, TableProcessing tableProcessing) {
        this.accountView = accountView;
        this.accountService = accountService;
        this.tableProcessing = tableProcessing;
        accountView.loadTable(tableProcessing.generateTable(accountService.findAllAccounts(), Constants.Columns.ACCOUNT_TABLE_COLUMNS));
        this.accountView.setBtnViewListener(new ViewListener());
        this.accountView.setBtnUpdateListener(new UpdateListener());
        this.accountView.setBtnDeleteListener(new DeleteListener());
    }

    @Override
    public Notification<Controller> getNextController(String selection) {
        return null;
    }

    @Override
    public void setVisibility(Boolean bool) {
        accountView.setVisibility(bool);
    }

    private class ViewListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Long id = Long.parseLong(accountView.getUniqueIdentifier());
            try {
                Notification<Account> viewAccountNotification = accountService.viewAccount(id);
                if (viewAccountNotification.hasErrors()) {
                    JOptionPane.showMessageDialog(accountView.getContentPane(), viewAccountNotification.getFormattedErrors());
                } else {
                    String[] columnNames = Constants.Columns.ACCOUNT_TABLE_COLUMNS;
                    Account account = viewAccountNotification.getResult();
                    List<Account> accounts = new ArrayList<>();
                    accounts.add(account);
                    JTable table = tableProcessing.generateTable(accounts, columnNames);
                    accountView.loadTable(table);
                }
            } catch (EntityNotFoundException exc) {
                JOptionPane.showMessageDialog(accountView.getContentPane(), "Viewing account not successful, please try again later.");
            }
        }
    }

    private class UpdateListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            List<Object> items = accountView.getSelectedRow();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
            Date date = new Date();
            try {
                date  = formatter.parse((String)items.get(3));
            } catch (ParseException exc) {
                exc.printStackTrace();
            }
            Account account = new AccountBuilder()
                    .setId(new Long((String)items.get(0)))
                    .setType((String)items.get(1))
                    .setSum(new Double((String)items.get(2)))
                    .setCreation_date(date)
                    .setId_client(new Long((String)items.get(4)))
                    .build();
            Notification<Boolean> updateAccountNotification = accountService.updateAccount(account);
            if (updateAccountNotification.hasErrors()) {
                JOptionPane.showMessageDialog(accountView.getContentPane(), updateAccountNotification.getFormattedErrors());
            } else {
                List<Account> accounts = accountService.findAllAccounts();
                accountView.loadTable(tableProcessing.generateTable(accounts, Constants.Columns.ACCOUNT_TABLE_COLUMNS));
            }
        }
    }

    private class DeleteListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            List<Object> items = accountView.getSelectedRow();
            Account account = new AccountBuilder()
                                .setId(new Long ((String)items.get(0)))
                                .build();
            if (!accountService.deleteAccount(account)){
                JOptionPane.showMessageDialog(accountView.getContentPane(), "Account could not be deleted. Please try again late!r");
            } else {
                List<Account> accounts = accountService.findAllAccounts();
                accountView.loadTable(tableProcessing.generateTable(accounts, Constants.Columns.ACCOUNT_TABLE_COLUMNS));
            }
        }
    }


}
