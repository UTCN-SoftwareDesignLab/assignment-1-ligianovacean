package controller;

import model.validation.Notification;

public interface Controller {

    Notification<Controller> getNextController(String selection);

    void setVisibility(Boolean bool);

}
