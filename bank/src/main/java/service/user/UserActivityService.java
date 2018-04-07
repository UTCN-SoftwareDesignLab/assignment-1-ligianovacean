package service.user;

import model.Activity;
import model.validation.Notification;
import repository.user.UserActivityRepository;

import java.util.Date;
import java.util.List;

public interface UserActivityService {

    boolean save(Activity activity);

    Notification<List<Activity>> findByDateAndUser(String username, Date startDate, Date endDate);

    void removeAll();

}
