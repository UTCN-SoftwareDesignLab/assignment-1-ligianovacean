package service.user;

import model.Role;
import model.User;
import model.validation.Notification;

import java.util.List;

public interface UserService {

    Notification<Boolean> createEmployee(String username, String password);

    Notification<User> viewEmployee(String username);

    Notification<Boolean> updateUser(Long id, String username);

    boolean deleteUser(User user);

    List<User> findAll();

    List<Role> getRoles(User user);

    void removeAll();
}
