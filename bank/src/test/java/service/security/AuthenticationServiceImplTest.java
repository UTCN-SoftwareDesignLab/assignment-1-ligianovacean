package service.security;

import database.Bootstrap;
import database.DBConnectionFactory;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.right_role.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;

import java.sql.SQLException;
import java.util.Collections;

import static database.Constants.Roles.EMPLOYEE;
import static org.junit.Assert.*;

public class AuthenticationServiceImplTest {

    private static AuthenticationService authenticationService;

    @BeforeClass
    public static void setup() throws SQLException {
        Bootstrap.dropAll();
        Bootstrap.bootstrapTables();
        Bootstrap.bootstrapUserData();

        authenticationService = new AuthenticationServiceImpl(
                new UserRepositoryMySQL(new DBConnectionFactory().getConnectionWrapper(true).getConnection(),
                        new RightsRolesRepositoryMySQL(new DBConnectionFactory().getConnectionWrapper(true).getConnection())),
                new RightsRolesRepositoryMySQL(new DBConnectionFactory().getConnectionWrapper(true).getConnection())
        );
    }

    @Test
    public void register() {
        authenticationService.register("ligia.novacean@yahoo.com", "qwerty.1", EMPLOYEE);
        UserRepository userRepository = new UserRepositoryMySQL(new DBConnectionFactory().getConnectionWrapper(true).getConnection(),
                                                                new RightsRolesRepositoryMySQL(new DBConnectionFactory().getConnectionWrapper(true).getConnection()));
        assertTrue(userRepository.findByUsername("ligia.novacean@yahoo.com").getResult().getId() == 1L);
    }

    @Test
    public void login() throws Exception{
        authenticationService.register("ligia@yahoo.com", "qwerty.1", EMPLOYEE);
        assertTrue(authenticationService.login("ligia@yahoo.com", "qwerty.1").getResult().getUsername().equals("ligia@yahoo.com"));
    }
}