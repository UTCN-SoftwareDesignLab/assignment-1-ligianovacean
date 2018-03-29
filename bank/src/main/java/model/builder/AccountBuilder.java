package model.builder;

import model.Account;

import java.util.Date;

public class AccountBuilder {

    Account account;

    public AccountBuilder() {
        this.account = new Account();
    }

    public AccountBuilder setId(Long id) {
        account.setId(id);
        return this;
    }

    public AccountBuilder setType(String type) {
        account.setType(type);
        return this;
    }

    public AccountBuilder setSum(Double sum) {
        account.setSum(sum);
        return this;
    }

    public AccountBuilder setCreation_date(Date date) {
        account.setCreation_date(date);
        return this;
    }

    public AccountBuilder setId_client(Long id_client) {
        account.setId_client(id_client);
        return this;
    }

    public Account build() {
        return account;
    }
}
