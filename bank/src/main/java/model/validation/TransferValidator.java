package model.validation;

import controller.TranferController;
import model.Account;

import java.util.ArrayList;
import java.util.List;

public class TransferValidator {

    private List<String> errors;
    private Account firstAccount;
    private Account secondAccount;
    private Double sum;

    public TransferValidator(Account firstAccount, Account secondAccount, Double sum){
        errors = new ArrayList<>();
        this.firstAccount = firstAccount;
        this.secondAccount = secondAccount;
        this.sum = sum;
    }

    public boolean validate() {
        validateSum(sum);
        validateTransfer(firstAccount.getSum(), sum);
        return errors.isEmpty();
    }

    private void validateSum(Double sum) {
        if (sum < 0) {
            errors.add("Sum must be positive");
        }
    }

    private void validateTransfer(Double accountSum, Double sum) {
        if (accountSum < sum) {
            errors.add("Not enough money in the account!");
        }
    }

    public List<String> getErrors(){
        return errors;
    }

}
