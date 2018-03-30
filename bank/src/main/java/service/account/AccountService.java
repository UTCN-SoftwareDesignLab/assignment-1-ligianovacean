package service.account;

import model.Account;
import model.validation.Notification;
import repository.EntityNotFoundException;

import java.util.List;

public interface AccountService {

    Notification<Boolean> save(Account account);

    Notification<Account> viewAccount(Long id) throws EntityNotFoundException;

    Notification<Boolean> updateAccount(Account account);

    List<Account> findAllAccounts();

    boolean deleteAccount(Account account);

    List<Account> findAccountsByClientId(Long id);

    public Notification<Boolean> transferBetweenAccounts(Long firstId, Long secondId, Double sum) throws EntityNotFoundException;

    public Notification<Boolean>  payBill(Long id, Double sum);
}
