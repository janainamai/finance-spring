package br.com.finance.authentication.validator;

import br.com.finance.authentication.repository.UserRepository;
import br.com.finance.authentication.infra.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserValidatorImpl implements UserValidator {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void validateSamePassword(String password1, String password2) {
        if (!password1.equals(password2)) {
            throw new BadRequestException("The passwords entered do not match");
        }
    }

    @Override
    public void validateUsernameAlreadyExists(String username) {
        if (userRepository.existsByLogin(username)) {
            throw new BadRequestException("There is already a user with that username");
        }
    }

}
