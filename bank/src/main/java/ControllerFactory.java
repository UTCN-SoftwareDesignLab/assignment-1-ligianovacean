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
import repository.user.UserActivityRepository;
import repository.user.UserActivityRepositoryMySQL;
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
import service.user.UserActivityService;
import service.user.UserActivityServiceImpl;
import service.user.UserService;
import service.user.UserServiceImpl;
import view.*;

import java.sql.Connection;
import java.util.HashMap;

import static database.Constants.Rights.*;
import static database.Constants.Roles.ADMINISTRATOR;
import static database.Constants.Roles.EMPLOYEE;

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
    private final GenerateReportController reportController;

    private final ClientService clientService;
    private final AccountService accountService;
    private final AuthenticationService authenticationService;
    private final BillPaymentService billService;
    private final UserService userService;
    private final UserActivityService activityService;

    private final ClientRepository clientRepository;
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final RightsRolesRepository rightsRolesRepository;
    private final BillRepository billRepository;
    private final UserActivityRepository activityRepository;

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
        this.activityRepository = new UserActivityRepositoryMySQL(connection);

        this.tableProcessing = tableProcessing;

        this.clientService = new ClientServiceImplementation(this.clientRepository);
        this.accountService = new AccountServiceImpl(this.accountRepository);
        this.authenticationService = new AuthenticationServiceImpl(this.userRepository, this.rightsRolesRepository);
        this.billService = new BillPaymentServiceImpl(this.accountRepository, this.billRepository);
        this.userService = new UserServiceImpl(this.userRepository, this.rightsRolesRepository);
        this.activityService = new UserActivityServiceImpl(activityRepository, rightsRolesRepository);

        this.addClientController = new AddClientController(new AddClientView(), clientService, activityService);
        this.RUDClientController = new RUDClientController(new RUDView(), clientService, tableProcessing, activityService);
        this.createAccountController = new CreateAccountController(new CreateAccountView(), accountService, clientService, tableProcessing, activityService);
        this.RUDaccountController = new RUDAccountController(new RUDView(), accountService, tableProcessing, activityService);
        this.transferController = new TranferController(new TransferView(), accountService, clientService, tableProcessing, activityService);
        this.billController = new ProcessUtilityBillsController(new ProcessUtilityBillsView(),
                billService, clientService, accountService, tableProcessing, activityService);
        this.createEmployeeController = new CreateEmployeeController(new CreateEmployeeView(), this.userService, activityService);
        this.rudEmployeeController = new RUDEmployeeController(new RUDView(), this.userService, this.tableProcessing, activityService);
        this.reportController = new GenerateReportController(activityService, new GenerateReportView(), tableProcessing);

        HashMap<String, Controller> employeeControllers = getEmployeeControllers();
        HashMap<String, Controller> adminControllers = getAdminControllers();
        this.employeeController = new EmployeeController(new EmployeeView(), employeeControllers, activityService);
        this.adminController = new AdminController(new AdministratorView(), adminControllers, activityService);
        HashMap<String, Controller> loginControllers = getLoginControllers();;
        this.loginController = new LoginController(new LoginView(), authenticationService, userService, loginControllers, activityService);

    }

    private HashMap<String, Controller> getLoginControllers() {
        HashMap<String, Controller> loginControllers = new HashMap<>();
        loginControllers.put(ADMINISTRATOR, this.adminController);
        loginControllers.put(EMPLOYEE, this.employeeController);
        return loginControllers;
    }

    private HashMap<String, Controller> getEmployeeControllers() {
        HashMap<String, Controller> employeeControllers = new HashMap<>();
        employeeControllers.put(CREATE_CLIENT, this.addClientController);
        employeeControllers.put(RUD_CLIENT, this.RUDClientController);
        employeeControllers.put(CREATE_ACCOUNT, this.createAccountController);
        employeeControllers.put(RUD_ACCOUNT, this.RUDaccountController);
        employeeControllers.put(TRANSFER_MONEY, this.transferController);
        employeeControllers.put(PROCESS_UTILITY_BILLS, this.billController);
        return employeeControllers;
    }

    private HashMap<String, Controller> getAdminControllers() {
        HashMap<String, Controller> adminControllers = new HashMap<>();
        adminControllers.put(CREATE_USER, this.createEmployeeController);
        adminControllers.put(RUD_USER, this.rudEmployeeController);
        adminControllers.put(GENERATE_REPORT, this.reportController);
        return adminControllers;
    }
}