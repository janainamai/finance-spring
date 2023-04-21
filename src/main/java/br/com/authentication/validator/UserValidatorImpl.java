package br.com.authentication.validator;

import br.com.authentication.exception.BadRequestException;

public class UserValidatorImpl implements UserValidator {

    @Override
    public void validateSamePassword(String password1, String password2) {
        if (!password1.equals(password2)) {
            throw new BadRequestException("As senhas informadas não coincidem");
        }
    }

}
