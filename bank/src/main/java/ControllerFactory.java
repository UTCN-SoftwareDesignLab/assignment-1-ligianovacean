import controller.AddClientController;
import controller.LoginController;
import controller.RUDClientController;
import controller.TableProcessing;
import database.DBConnectionFactory;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;
import repository.right_role.RightsRolesRepository;
import repository.right_role.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.client.ClientService;
import service.client.ClientServiceImplementation;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceImpl;
import view.AddClientView;
import view.LoginView;
import view.RUDView;

import java.sql.Connection;

public class ControllerFactory {

    private final AddClientController addClientController;
    private final RUDClientController RUDClientController;
    private final LoginController loginController;

    private final ClientService clientService;
    private final AuthenticationService authenticationService;

    private final ClientRepository clientRepository;
    private final UserRepository userRepository;
    private final RightsRolesRepository rightsRolesRepository;

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

        this.clientRepository = new ClientRepositoryMySQL(connection);
        this.rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        this.userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);

        this.tableProcessing = tableProcessing;

        this.clientService = new ClientServiceImplementation(this.clientRepository);
        this.authenticationService = new AuthenticationServiceImpl(this.userRepository, this.rightsRolesRepository);

        this.loginController = new LoginController(new LoginView(), authenticationService);
        this.addClientController = new AddClientController(new AddClientView(), clientService);
        this.RUDClientController = new RUDClientController(new RUDView(), clientService, tableProcessing);
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