package service.account;

import database.Bootstrap;
import database.DBConnectionFactory;
import model.Account;
import model.Client;
import model.builder.AccountBuilder;
import model.builder.ClientBuilder;
import model.validation.Notification;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.EntityNotFoundException;
import repository.account.AccountRepositoryMySQL;
import repository.client.ClientRepositoryMySQL;
import service.client.ClientService;
import service.client.ClientServiceImplementation;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static org.junit.Assert.*;

public class AccountServiceImplTest {

    private static AccountService accountService;

    @BeforeClass
    public static void setup() throws SQLException{
        Bootstrap.dropAll();
        Bootstrap.bootstrapTables();
        Bootstrap.bootstrapUserData();

        accountService = new AccountServiceImpl(new AccountRepositoryMySQL(
                new DBConnectionFactory().getConnectionWrapper(true).getConnection()
        ));

        Client client = new ClientBuilder()
                .setAddress("Alba Iulia")
                .setPersonal_numerical_code("1234567891")
                .setName("Novacean Ligia")
                .setId_card_no("1234")
                .build();
        ClientService clientService = new ClientServiceImplementation(new ClientRepositoryMySQL(
                new DBConnectionFactory().getConnectionWrapper(true).getConnection()
        ));
        clientService.save(client);
    }

    @Before
    public void clearUp() {
        accountService.removeAll();
    }

    @Test
    public void save() throws Exception{
        assertTrue(accountService.save(
                new AccountBuilder()
                        .setId_client(1L)
                        .setSum(0D)
                        .setCreation_date((new GregorianCalendar(2004, 5, 10).getTime()))
                        .setType("Savings account")
                        .build()
                ).getResult());
    }

    @Test
    public void viewAccount() throws EntityNotFoundException {
        Account account = new AccountBuilder()
                .setId_client(1L)
                .setSum(0D)
                .setCreation_date((new GregorianCalendar(2004, 5, 10).getTime()))
                .setType("Savings account")
                .build();
        accountService.save(account);
        accountService.save(account);

        assertTrue(accountService.viewAccount(2L).getResult() != null);
    }

    @Test
    public void updateAccount() throws Exception{
        Account account = new AccountBuilder()
                .setId_client(1L)
                .setSum(0D)
                .setCreation_date((new GregorianCalendar(2004, 5, 10).getTime()))
                .setType("Savings account")
                .build();
        accountService.save(account);

        account.setSum(20D);
        account.setId(12L);
        accountService.updateAccount(account);
        assertTrue(accountService.findAllAccounts().get(0).getSum() == 20D);
    }

    @Test
    public void findAll() throws Exception{
        List<Account> accounts = accountService.findAllAccounts();
        assertTrue(accounts.size() == 0);
    }

    @Test
    public void findAllAccountsWhenDbNotEmpty() throws Exception{
        Account account = new AccountBuilder()
                .setId_client(1L)
                .setSum(0D)
                .setCreation_date((new GregorianCalendar(2004, 5, 10).getTime()))
                .setType("Savings account")
                .build();

        accountService.save(account);
        accountService.save(account);
        accountService.save(account);
        List<Account> accounts = accountService.findAllAccounts();
        assertTrue(accounts.size() == 3);
    }

    @Test
    public void deleteAccount() throws Exception{
        Account account = new AccountBuilder()
                .setId(1L)
                .setId_client(1L)
                .setSum(0D)
                .setCreation_date((new GregorianCalendar(2004, 5, 10).getTime()))
                .setType("Savings account")
                .build();
        int initialSize = accountService.findAllAccounts().size();
        accountService.save(account);

        accountService.deleteAccount(account);
        assertTrue(accountService.findAllAccounts().size() == initialSize);
    }

    @Test
    public void findAccountsByClientId() throws Exception{
        Account account = new AccountBuilder()
                .setId_client(1L)
                .setSum(0D)
                .setCreation_date((new GregorianCalendar(2004, 5, 10).getTime()))
                .setType("Savings account")
                .build();
        accountService.save(account);
        accountService.save(account);
        List<Account> accounts = accountService.findAccountsByClientId(1L);
        assertTrue(accounts.size() == 2);
    }

    @Test
    public void transferBetweenAccounts() throws Exception{
        Account account = new AccountBuilder()
                .setId_client(1L)
                .setSum(0D)
                .setCreation_date((new GregorianCalendar(2004, 5, 10).getTime()))
                .setType("Savings account")
                .build();
        accountService.save(account);
        account.setSum(200D);
        accountService.save(account);
        accountService.transferBetweenAccounts(6L, 5L, 50D);
        assertTrue(accountService.viewAccount(6L).getResult().getSum() == 150D);
    }

}