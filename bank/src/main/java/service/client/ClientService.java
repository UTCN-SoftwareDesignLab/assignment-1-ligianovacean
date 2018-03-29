package service.client;

import model.Client;
import model.validation.Notification;
import repository.EntityNotFoundException;

public interface ClientService {

    Notification<Boolean> save(Client client);

    Notification<Client> viewClient(Long id) throws EntityNotFoundException;

}
