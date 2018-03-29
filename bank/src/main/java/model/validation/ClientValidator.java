package model.validation;

import model.Client;

import java.util.ArrayList;
import java.util.List;

public class ClientValidator {

    private final List<String> errors;

    private final Client client;

    public ClientValidator(Client client) {
        errors = new ArrayList<>();
        this.client = client;
    }

    public boolean validate() {
        validatePersonalNumericalCode(client.getPersonal_numerical_code());
        validateName(client.getName());
        return errors.isEmpty();
    }

    private void validateName(String name) {
        String[] names = name.split(" ");
        if (names.length < 2)
            errors.add("Please enter both name and surname!");
    }

    // ALL digits!!!!
    private void validatePersonalNumericalCode(String pnc) {
        if (pnc.length() != 13) {
            errors.add("PNC must be of length 13!");
        }
    }

    public List<String> getErrors() {
        return errors;
    }
}
