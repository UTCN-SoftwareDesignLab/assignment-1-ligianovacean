package model.builder;

import model.Account;
import model.Client;

import java.util.List;

public class ClientBuilder {

    private Client client;

    public ClientBuilder() {
        this.client = new Client();
    }

    public ClientBuilder setId(Long id) {
        client.setId(id);
        return this;
    }

    public ClientBuilder setName(String name) {
        client.setName(name);
        return this;
    }

    public ClientBuilder setId_card_no(String id_card_no) {
        client.setId_card_no(id_card_no);
        return this;
    }

    public ClientBuilder setPersonal_numerical_code(String personal_numerical_code) {
        client.setPersonal_numerical_code(personal_numerical_code);
        return this;
    }

    public ClientBuilder setAddress(String address) {
        client.setAddress(address);
        return this;
    }

    public ClientBuilder setAccounts(List<Account> accounts) {
        client.setAccounts(accounts);
        return this;
    }

    public Client build() {
        return client;
    }

}
