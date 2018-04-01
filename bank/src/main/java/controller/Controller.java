package controller;

import model.validation.Notification;

import java.util.List;

public interface Controller {

    Notification<Controller> getNextController(String selection);

    void setVisibility(Boolean bool);

}
