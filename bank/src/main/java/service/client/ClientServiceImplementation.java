package service.client;

import model.Account;
import model.Client;
import model.validation.ClientValidator;
import model.validation.Notification;
import repository.EntityNotFoundException;
import repository.client.ClientRepository;

import javax.swing.text.html.parser.Entity;
import java.util.List;

public class ClientServiceImplementation implements ClientService {

    private final ClientRepository clientRepository;

    public ClientServiceImplementation(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Notification<Boolean> save(Client client) {
        ClientValidator clientValidator = new ClientValidator(client);
        boolean clientValid = clientValidator.validate();
        Notification<Boolean> addClientNotification = new Notification<>();

        if (!clientValid) {
            clientValidator.getErrors().forEach(addClientNotification::addError);
            addClientNotification.setResult(Boolean.FALSE);
        } else {
            addClientNotification.setResult(clientRepository.save(client));
        }

        return addClientNotification;
    }

    @Override
    public Notification<Client> viewClient(Long id) throws EntityNotFoundException {
        return clientRepository.findById(id);
    }

    public Notification<Boolean> updateClient(Client client) {
        ClientValidator clientValidator = new ClientValidator(client);
        boolean clientValid = clientValidator.validate();
        Notification<Boolean> updateClientNotification = new Notification<>();

        if (!clientValid) {
            clientValidator.getErrors().forEach(updateClientNotification::addError);
            updateClientNotification.setResult(Boolean.FALSE);
        } else {
            updateClientNotification.setResult(clientRepository.update(client));
        }
        return updateClientNotification;
    }

    public List<Client> findAllClients() {
        return clientRepository.findAll();
    }

}
