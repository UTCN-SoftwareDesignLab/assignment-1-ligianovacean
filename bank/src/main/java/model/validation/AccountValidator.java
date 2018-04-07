package model.validation;

import model.Account;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * good idea to validate the date
 */
public class AccountValidator {

    //private static final String DATE_REGEX_VALIDATOR = ^(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.]((19|20)\\d\\d)$
    private static final String SAVINGS = "Savings account";
    private static final String SPENDING = "Spending account";

    private final List<String> errors;

    private final Account account;

    public AccountValidator(Account account) {
        errors = new ArrayList<>();
        this.account = account;
    }

    public boolean validate() {
        validateType(account.getType());
        validateDate(account.getCreation_date());
        return errors.isEmpty();
    }

    private void validateDate(Date creation_date) {
        if (creation_date.after(new Date()))
            errors.add("Data cannot be correct!");
    }

    private void validateType(String type) {
        if (!(type.equals(SAVINGS) || type.equals(SPENDING)))
            errors.add("Account type must be 'Spending account' or 'Savings account'");
    }

    public List<String> getErrors() {
        return errors;
    }
}
