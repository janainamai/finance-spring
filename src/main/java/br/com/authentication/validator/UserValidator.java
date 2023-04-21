package br.com.authentication.validator;

public interface UserValidator {

    void validateSamePassword(String password1, String password2);

}
