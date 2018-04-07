package repository.user;

import model.Activity;
import model.Role;
import model.validation.Notification;

import java.sql.Date;
import java.util.List;

public interface UserActivityRepository {

    boolean save(Activity activity);

    List<Activity> findByDateAndUser(Date startDate, Date endDate, String username);

    void removeAll();

}
