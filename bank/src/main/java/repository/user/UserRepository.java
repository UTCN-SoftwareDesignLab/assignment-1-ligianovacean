package repository.user;

import model.User;
import model.validation.Notification;

import java.util.List;

public interface UserRepository {

    List<User> findAll();

    Notification<User> findByUsernameAndPassword(String username, String password) throws AuthenticationException;

    Notification<User> findByUsername(String username);

    boolean save(User user);

    boolean update(Long id, String username);

    public boolean delete(User user);

    void removeAll();

}
