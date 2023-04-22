package br.com.authentication.validator;

import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Component;

@Component
public class UserValidatorImpl implements UserValidator {

    @Override
    public void validateSamePassword(String password1, String password2) {
        if (!password1.equals(password2)) {
            throw new ServiceException("As senhas informadas não coincidem");
        }
    }

}
