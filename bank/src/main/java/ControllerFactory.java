import controller.*;
import database.DBConnectionFactory;
import repository.account.AccountRepository;
import repository.account.AccountRepositoryMySQL;
import repository.bill.BillRepository;
import repository.bill.BillRepositoryMySQL;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;
import repository.right_role.RightsRolesRepository;
import repository.right_role.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.account.AccountService;
import service.account.AccountServiceImpl;
import service.bill.payment.BillPaymentService;
import service.bill.payment.BillPaymentServiceImpl;
import service.client.ClientService;
import service.client.ClientServiceImplementation;
import service.security.AuthenticationService;
import service.security.AuthenticationServiceImpl;
import service.user.UserService;
import service.user.UserServiceImpl;
import view.*;

import java.sql.Connection;
import java.util.HashMap;

public class ControllerFactory {

    private final AddClientController addClientController;
    private final RUDClientController RUDClientController;
    private final LoginController loginController;
    private final CreateAccountController createAccountController;
    private final RUDAccountController RUDaccountController;
    private final TranferController transferController;
    private final ProcessUtilityBillsController billController;
    private final EmployeeController employeeController;
    private final AdminController adminController;
    private final CreateEmployeeController createEmployeeController;
    private final RUDEmployeeController rudEmployeeController;

    private final ClientService clientService;
    private final AccountService accountService;
    private final AuthenticationService authenticationService;
    private final BillPaymentService billService;
    private final UserService userService;

    private final ClientRepository clientRepository;
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final RightsRolesRepository rightsRolesRepository;
    private final BillRepository billRepository;

    private static ControllerFactory instance;

    private static TableProcessing tableProcessing;

    public static ControllerFactory instance(Boolean componentsForTests, TableProcessing tableProcessing) {
        if (instance == null) {
            instance = new ControllerFactory(componentsForTests, tableProcessing);
        }
        return instance;
    }

    public ControllerFactory(Boolean componentsForTests, TableProcessing tableProcessing) {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(componentsForTests).getConnection();

        this.accountRepository = new AccountRepositoryMySQL(connection);
        this.clientRepository = new ClientRepositoryMySQL(connection);
        this.rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        this.userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
        this.billRepository = new BillRepositoryMySQL(connection);

        this.tableProcessing = tableProcessing;

        this.clientService = new ClientServiceImplementation(this.clientRepository);
        this.accountService = new AccountServiceImpl(this.accountRepository);
        this.authenticationService = new AuthenticationServiceImpl(this.userRepository, this.rightsRolesRepository);
        this.billService = new BillPaymentServiceImpl(this.accountRepository, this.billRepository);
        this.userService = new UserServiceImpl(this.userRepository, this.rightsRolesRepository);

        this.addClientController = new AddClientController(new AddClientView(), clientService);
        this.RUDClientController = new RUDClientController(new RUDView(), clientService, tableProcessing);
        this.createAccountController = new CreateAccountController(new CreateAccountView(), accountService, clientService, tableProcessing);
        this.RUDaccountController = new RUDAccountController(new RUDView(), accountService, tableProcessing);
        this.transferController = new TranferController(new TransferView(), accountService, clientService, tableProcessing);
        this.billController = new ProcessUtilityBillsController(new ProcessUtilityBillsView(),
                billService, clientService, accountService, tableProcessing);
        this.createEmployeeController = new CreateEmployeeController(new CreateEmployeeView(), this.userService);
        this.rudEmployeeController = new RUDEmployeeController(new RUDView(), this.userService, this.tableProcessing);

        HashMap<String, Controller> employeeControllers = getEmployeeControllers();
        HashMap<String, Controller> adminControllers = getAdminControllers();

        this.employeeController = new EmployeeController(new EmployeeView(), employeeControllers);
        this.adminController = new AdminController(new AdministratorView(), adminControllers);
        this.loginController = new LoginController(new LoginView(), authenticationService, employeeController, adminController);

    }

    private HashMap<String, Controller> getEmployeeControllers() {
        HashMap<String, Controller> employeeControllers = new HashMap<>();
        employeeControllers.put("AddClient", this.addClientController);
        employeeControllers.put("RUDClient", this.RUDClientController);
        employeeControllers.put("CreateAccount", this.createAccountController);
        employeeControllers.put("RUDAccount", this.RUDaccountController);
        employeeControllers.put("TransferMoney", this.transferController);
        employeeControllers.put("ProcessUtilityBills", this.billController);
        return employeeControllers;
    }

    private HashMap<String, Controller> getAdminControllers() {
        HashMap<String, Controller> adminControllers = new HashMap<>();
        adminControllers.put("CreateEmployee", this.createEmployeeController);
        adminControllers.put("RUDEmployee", this.rudEmployeeController);
        return adminControllers;
    }

    public AddClientController getAddClientController() {
        return addClientController;
    }

    public RUDClientController getRUDClientController() {
        return RUDClientController;
    }

    public LoginController getLoginController() {
        return loginController;
    }
}