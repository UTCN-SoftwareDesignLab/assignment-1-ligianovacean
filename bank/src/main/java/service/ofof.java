package service;

import model.User;
import model.validation.Notification;
import repository.user.AuthenticationException;

public interface ofof {

    Notification<Boolean> register(String username, String password);

    Notification<User> login(String username, String password) throws AuthenticationException;

    boolean logout(User user);
}
