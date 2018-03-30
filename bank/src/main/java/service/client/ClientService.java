package service.client;

import model.Client;
import model.validation.Notification;
import repository.EntityNotFoundException;

import java.util.List;

public interface ClientService {

    Notification<Boolean> save(Client client);

    Notification<Client> viewClient(Long id) throws EntityNotFoundException;

    Notification<Boolean> updateClient(Client client);

    List<Client> findAllClients();

}
