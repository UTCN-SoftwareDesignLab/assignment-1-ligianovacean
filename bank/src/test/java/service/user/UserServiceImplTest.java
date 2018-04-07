package service.user;

import database.Bootstrap;
import database.DBConnectionFactory;
import model.User;
import model.builder.UserBuilder;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.right_role.RightsRolesRepositoryMySQL;
import repository.user.UserRepositoryMySQL;
import service.security.AuthenticationService;
import service.security.AuthenticationServiceImpl;

import java.sql.SQLException;

import static database.Constants.Roles.ADMINISTRATOR;
import static database.Constants.Roles.EMPLOYEE;
import static org.junit.Assert.*;

public class UserServiceImplTest {

    private static UserService userService;

    @BeforeClass
    public static void setup() throws SQLException {
        Bootstrap.dropAll();
        Bootstrap.bootstrapTables();
        Bootstrap.bootstrapUserData();

        userService = new UserServiceImpl(
                new UserRepositoryMySQL(new DBConnectionFactory().getConnectionWrapper(true).getConnection(),
                        new RightsRolesRepositoryMySQL(new DBConnectionFactory().getConnectionWrapper(true).getConnection())),
                new RightsRolesRepositoryMySQL(new DBConnectionFactory().getConnectionWrapper(true).getConnection())
        );

        AuthenticationService authenticationService;
        authenticationService = new AuthenticationServiceImpl(
                new UserRepositoryMySQL(new DBConnectionFactory().getConnectionWrapper(true).getConnection(),
                        new RightsRolesRepositoryMySQL(new DBConnectionFactory().getConnectionWrapper(true).getConnection())),
                new RightsRolesRepositoryMySQL(new DBConnectionFactory().getConnectionWrapper(true).getConnection())
        );
        authenticationService.register("ligia.novacean@yahoo.com", "qwerty.1", EMPLOYEE);
        authenticationService.register("anca.faur@yahoo.com", "qwerty.1", ADMINISTRATOR);
        authenticationService.register("ana.han@yahoo.com", "qwerty.1", EMPLOYEE);
        authenticationService.register("ana.han@yahoo.com", "qwerty.1", ADMINISTRATOR);
        authenticationService.register("carina.sirbu@yahoo.com", "qwerty.1", EMPLOYEE);
    }

    @Test
    public void getRoles() {
        User user = userService.viewEmployee("ana.han@yahoo.com").getResult();
        assertTrue(userService.getRoles(user).size() == 2);
    }

    @Test
    public void createEmployee() {
        userService.createEmployee("anca.faur@yahoo.com", "qwerty.1");
        assertTrue(userService.findAll().size() == 4);
    }

    @Test
    public void viewEmployee() {
        assertTrue(userService.viewEmployee("ana.han@yahoo.com").getResult().getId() == 3L);
    }

    @Test
    public void updateUser() {
        userService.updateUser(2L, "oana_cristian@yahoo.com");
        assertTrue(userService.viewEmployee("oana_cristian@yahoo.com").getResult() != null);
    }

    @Test
    public void deleteUser() {
        userService.deleteUser(new UserBuilder().setId(4L).build());
        assertTrue(userService.findAll().size() == 3);
    }

    @Test
    public void findAll() {
        assertTrue(userService.findAll().size() == 4);
    }
}