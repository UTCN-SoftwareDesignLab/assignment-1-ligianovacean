package model.validation;

import model.Account;
import model.Bill;

import java.util.ArrayList;
import java.util.List;

public class PaymentValidator {

    private final List<String> errors;

    private final Bill bill;
    private final Account account;

    public PaymentValidator(Bill bill, Account account) {
        errors = new ArrayList<>();
        this.bill = bill;
        this.account = account;
    }

    public boolean validate(){
        validateSum(bill.getSum());
        validateAccount(account.getSum(), bill.getSum());
        return errors.isEmpty();
    }

    private void validateSum(Double sum) {
        if (sum < 0) {
            errors.add("Sum must be positive!");
        }
    }

    private void validateAccount(Double accountSum, Double sum) {
        if (accountSum < sum) {
            errors.add("Not enough money in the account!");
        }
    }

    public List<String> getErrors() {
        return errors;
    }
}
