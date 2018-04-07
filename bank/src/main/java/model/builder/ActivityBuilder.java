package model.builder;

import model.Activity;

import java.util.Date;

public class ActivityBuilder {

    private Activity activity;

    public ActivityBuilder() {
        this.activity = new Activity();
    }

    public ActivityBuilder setId(Long id) {
        activity.setId(id);
        return this;
    }

    public ActivityBuilder setUserRole(String role) {
        activity.setUserRole(role);
        return this;
    }

    public ActivityBuilder setUserId(Long id) {
        activity.setUserId(id);
        return this;
    }

    public ActivityBuilder setDate(Date date) {
        activity.setDate(date);
        return this;
    }

    public ActivityBuilder setDescription(String description) {
        activity.setDescription(description);
        return this;
    }

    public Activity build(){
        return activity;
    }
}
