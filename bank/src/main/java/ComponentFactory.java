import database.DBConnectionFactory;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;
import repository.right_role.RightsRolesRepository;
import repository.right_role.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.client.ClientService;
import service.client.ClientServiceImplementation;
import service.security.AuthenticationService;
import service.security.AuthenticationServiceImpl;

import java.sql.Connection;

public class ComponentFactory {

    private final AuthenticationService authenticationService;
    private final ClientService clientService;

    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final RightsRolesRepository rightsRolesRepository;

    private static ComponentFactory instance;

    public static ComponentFactory instance(Boolean componentsForTests) {
        if (instance == null) {
            instance = new ComponentFactory(componentsForTests);
        }
        return instance;
    }

    private ComponentFactory(Boolean componentsForTests) {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(componentsForTests).getConnection();
        this.rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        this.userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
        this.clientRepository = new ClientRepositoryMySQL(connection);
        this.authenticationService = new AuthenticationServiceImpl(this.userRepository, this.rightsRolesRepository);
        this.clientService  = new ClientServiceImplementation(this.clientRepository);
    }

    public AuthenticationService getAuthenticationService() {
        return authenticationService;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public RightsRolesRepository getRightsRolesRepository() {
        return rightsRolesRepository;
    }

    public ClientService getClientService() {
        return clientService;
    }
}
