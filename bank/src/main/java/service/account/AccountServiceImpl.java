package service.account;

import model.Account;
import model.validation.AccountValidator;
import model.validation.ClientValidator;
import model.validation.Notification;
import model.validation.TransferValidator;
import repository.EntityNotFoundException;
import repository.account.AccountRepository;


import java.util.List;

public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Notification<Boolean> save(Account account) {
        AccountValidator accountValidator = new AccountValidator(account);
        boolean accountValid = accountValidator.validate();
        Notification<Boolean> createAccountNotification = new Notification<>();

        if (!accountValid) {
            accountValidator.getErrors().forEach(createAccountNotification::addError);
            createAccountNotification.setResult(Boolean.FALSE);
        } else {
            createAccountNotification.setResult(accountRepository.save(account));
        }

        return createAccountNotification;
    }

    @Override
    public Notification<Account> viewAccount(Long id) throws EntityNotFoundException {
        return accountRepository.findById(id);
    }

    @Override
    public Notification<Boolean> updateAccount(Account account) {
        AccountValidator accountValidator = new AccountValidator(account);
        boolean accountValid = accountValidator.validate();
        Notification<Boolean> updateAccountNotification = new Notification<>();

        if (!accountValid) {
            accountValidator.getErrors().forEach(updateAccountNotification::addError);
            updateAccountNotification.setResult(Boolean.FALSE);
        } else {
            updateAccountNotification.setResult(accountRepository.update(account));
        }
        return updateAccountNotification;
    }

    @Override
    public List<Account> findAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public boolean deleteAccount(Account account) {
        return accountRepository.delete(account);
    }

    @Override
    public List<Account> findAccountsByClientId(Long id) {
        return accountRepository.findAccountsByClientId(id);
    }

    @Override
    public Notification<Boolean> transferBetweenAccounts(Long firstId, Long secondId, Double sum) {
        Notification<Boolean> result = new Notification<>();
        try {
            Account firstAccount = accountRepository.findById(firstId).getResult();
            Account secondAccount = accountRepository.findById(secondId).getResult();
            Double firstSum = firstAccount.getSum();
            Double secondSum = secondAccount.getSum();

            TransferValidator transferValidator = new TransferValidator(firstAccount, secondAccount, sum);
            boolean transferValid = transferValidator.validate();
            if (!transferValid) {
                transferValidator.getErrors().forEach(result::addError);
                result.setResult(Boolean.FALSE);
            } else {
                firstSum -= sum;
                secondSum += sum;
                firstAccount.setSum(firstSum);
                secondAccount.setSum(secondSum);
                accountRepository.update(firstAccount);
                accountRepository.update(secondAccount);
                result.setResult(Boolean.TRUE);
            }
        } catch (EntityNotFoundException exc) {
            result.addError("Transfer could not be processed! Make sure the id of the second account is a valid one!");
        }

        return result;
    }

    @Override
    public void removeAll() {
        accountRepository.removeAll();
    }

}
