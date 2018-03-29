package repository.client;

import model.Client;
import model.User;
import model.validation.Notification;
import repository.EntityNotFoundException;
import repository.user.AuthenticationException;

import javax.swing.text.html.parser.Entity;
import java.util.List;

public interface ClientRepository {

    List<Client> findAll();

    Notification<Client> findById(Long id) throws EntityNotFoundException;

    boolean save(Client client);

    boolean update(Client client);

    void removeAll();
}
