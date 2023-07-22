package br.com.finance.authentication.validators;

public interface UserValidator {

    void validateSamePassword(String password1, String password2);

    void validateUsernameAlreadyExists(String username);
}
