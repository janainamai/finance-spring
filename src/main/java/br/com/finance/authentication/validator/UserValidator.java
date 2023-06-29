package br.com.finance.authentication.validator;

public interface UserValidator {

    void validateSamePassword(String password1, String password2);

    void validateUsernameAlreadyExists(String username);
}
