package repository.account;

import model.Account;
import repository.EntityNotFoundException;

import java.util.List;

public interface AccountRepository {

    List<Account> findAll();

    Account findById(Long id) throws EntityNotFoundException;

    boolean save(Account account);

    void removeAll();

}
