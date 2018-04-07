package controller;

import model.Activity;
import model.builder.ActivityBuilder;
import model.validation.Notification;
import service.user.UserActivityService;
import view.View;

import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.Date;

public abstract class Controller {

    private Long loggedInUser;
    private UserActivityService activityService;

    public Controller(UserActivityService activityService) {
        this.activityService = activityService;
        loggedInUser = -1L;
    }

    public abstract void setVisibility(Boolean bool);

    public void setLoggedInUser(Long id) {
        this.loggedInUser = id;
    }

    public Long getLoggedInUser() {
        return loggedInUser;
    }

    public void registerActivity(Long userId, Date date, String description, String role) {
        Activity activity = new ActivityBuilder()
                                .setDate(date)
                                .setDescription(description)
                                .setUserId(userId)
                                .setUserRole(role)
                                .build();
        activityService.save(activity);
    }

    public UserActivityService getActivityService() {
        return activityService;
    }
}
