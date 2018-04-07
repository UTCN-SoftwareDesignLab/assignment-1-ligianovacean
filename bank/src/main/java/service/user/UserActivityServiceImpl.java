package service.user;

import model.Activity;
import model.Role;
import model.validation.Notification;
import repository.right_role.RightsRolesRepository;
import repository.user.UserActivityRepository;
import repository.user.UserRepository;

import java.sql.*;
import java.util.Date;
import java.util.List;

import static database.Constants.Roles.ADMINISTRATOR;
import static database.Constants.Roles.EMPLOYEE;

public class UserActivityServiceImpl implements UserActivityService{

    UserActivityRepository userActivityRepository;
    RightsRolesRepository rightsRolesRepository;

    public UserActivityServiceImpl(UserActivityRepository userActivityRepository, RightsRolesRepository rightsRolesRepository) {
        this.userActivityRepository = userActivityRepository;
        this.rightsRolesRepository = rightsRolesRepository;
    }

    @Override
    public boolean save(Activity activity) {
        return userActivityRepository.save(activity);

    }

    @Override
    public Notification<List<Activity>> findByDateAndUser(String username, Date startDate, Date endDate) {
        List<Role> roles = rightsRolesRepository.findRolesForUser(username);
        Notification<List<Activity>> notification = new Notification<>();
//        for (Role role : roles) {
//            if (role.getRole().equals(ADMINISTRATOR)) {
//                notification.addError("You can generate report for users whose only role is of employee!");
//                return notification;
//            }
//        }
        java.sql.Date newStartDate = new java.sql.Date(startDate.getTime());
        java.sql.Date newEndDate = new java.sql.Date(endDate.getTime());
        List<Activity> activities = userActivityRepository.findByDateAndUser(newStartDate, newEndDate, username);
        notification.setResult(activities);
        return notification;
    }

    @Override
    public void removeAll() {
        userActivityRepository.removeAll();
    }
}
