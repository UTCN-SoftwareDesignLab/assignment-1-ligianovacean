package service.bill.payment;

import database.Bootstrap;
import database.DBConnectionFactory;
import model.Account;
import model.Bill;
import model.Client;
import model.builder.AccountBuilder;
import model.builder.BillBuilder;
import model.builder.ClientBuilder;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.account.AccountRepositoryMySQL;
import repository.bill.BillRepositoryMySQL;
import repository.client.ClientRepositoryMySQL;
import service.account.AccountService;
import service.account.AccountServiceImpl;
import service.client.ClientService;
import service.client.ClientServiceImplementation;

import java.sql.SQLException;
import java.util.GregorianCalendar;

import static org.junit.Assert.*;

public class BillPaymentServiceImplTest {

    private static BillPaymentService billPaymentService;

    @BeforeClass
    public static void setup() throws SQLException {
        Bootstrap.dropAll();
        Bootstrap.bootstrapTables();
        Bootstrap.bootstrapUserData();

        billPaymentService = new BillPaymentServiceImpl(new AccountRepositoryMySQL(
                new DBConnectionFactory().getConnectionWrapper(true).getConnection()),
                new BillRepositoryMySQL(new DBConnectionFactory().getConnectionWrapper(true).getConnection())
        );

        //Create client
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

        //Create account
        Account account = new AccountBuilder()
                            .setType("Savings account")
                            .setCreation_date((new GregorianCalendar(2004, 5, 10).getTime()))
                            .setSum(50D)
                            .setId_client(1L)
                            .build();
        AccountService accountService = new AccountServiceImpl(new AccountRepositoryMySQL(
                new DBConnectionFactory().getConnectionWrapper(true).getConnection()
        ));
        accountService.save(account);
    }

    @Before
    public void cleanUp() {billPaymentService.removeAll();}

    @Test
    public void payBill() {
        Bill bill = new BillBuilder()
                        .setSum(15D)
                        .setIdentifier("January bill")
                        .setIdClient(1L)
                        .build();
        assertTrue(billPaymentService.payBill(bill, 1L, 15D).getResult());
    }
}