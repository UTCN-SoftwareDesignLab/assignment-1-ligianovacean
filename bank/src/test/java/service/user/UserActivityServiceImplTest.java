package service.user;

import com.mysql.jdbc.JDBC4Connection;
import database.Bootstrap;
import database.DBConnectionFactory;
import model.Activity;
import model.User;
import model.builder.ActivityBuilder;
import model.builder.UserBuilder;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.right_role.RightsRolesRepositoryMySQL;
import repository.user.UserActivityRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.security.AuthenticationService;
import service.security.AuthenticationServiceImpl;
import sun.util.calendar.Gregorian;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import static database.Constants.Roles.ADMINISTRATOR;
import static database.Constants.Roles.EMPLOYEE;
import static org.junit.Assert.*;

public class UserActivityServiceImplTest {

    private static UserActivityService userActivityService;

    @BeforeClass
    public static void setup() throws SQLException {
        Bootstrap.dropAll();
        Bootstrap.bootstrapTables();
        Bootstrap.bootstrapUserData();

        userActivityService = new UserActivityServiceImpl(new UserActivityRepositoryMySQL(
                                        new DBConnectionFactory().getConnectionWrapper(true).getConnection()
        ), new RightsRolesRepositoryMySQL(new DBConnectionFactory().getConnectionWrapper(true).getConnection()));

        AuthenticationService authenticationService;
        authenticationService = new AuthenticationServiceImpl(
                new UserRepositoryMySQL(new DBConnectionFactory().getConnectionWrapper(true).getConnection(),
                        new RightsRolesRepositoryMySQL(new DBConnectionFactory().getConnectionWrapper(true).getConnection())),
                new RightsRolesRepositoryMySQL(new DBConnectionFactory().getConnectionWrapper(true).getConnection())
        );
        authenticationService.register("ligia.novacean@yahoo.com", "qwerty.1", EMPLOYEE);
    }

    @Before
    public void cleanUp() {
        userActivityService.removeAll();
    }

    @Test
    public void save() throws Exception{
        Activity activity = new ActivityBuilder()
                                .setUserId(1L)
                                .setDate(new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).parse("February 10, 2018"))
                                .setDescription("First activity")
                                .setUserRole(EMPLOYEE)
                                .build();
        userActivityService.save(activity);
        List<Activity> activities = userActivityService.findByDateAndUser("ligia.novacean@yahoo.com",
                new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).parse("February 3, 2018"),
                new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).parse("February 25, 2018")).getResult();
        assertTrue(activities.size() == 1);
    }

    @Test
    public void findByDateAndUser() throws Exception{
        Activity activity = new ActivityBuilder()
                .setUserId(1L)
                .setDate(new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).parse("February 10, 2018"))
                .setDescription("First activity")
                .setUserRole(EMPLOYEE)
                .build();

        userActivityService.save(activity);
        activity.setDate(new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).parse("February 28, 2018"));
        userActivityService.save(activity);
        activity.setDate(new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).parse("February 15, 2018"));
        activity.setUserRole(ADMINISTRATOR);
        userActivityService.save(activity);
        List<Activity> activities = userActivityService.findByDateAndUser("ligia.novacean@yahoo.com",
                new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).parse("February 3, 2018"),
                new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).parse("February 25, 2018")).getResult();
        assertTrue(activities.size() == 1);
    }
}