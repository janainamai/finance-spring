package br.com.planner.authentication.validator;

import br.com.planner.authentication.exception.BadRequestException;
import br.com.planner.authentication.repository.UserRepository;
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
        if (userRepository.existsByUsername(username)) {
            throw new BadRequestException("There is already a user with that username");
        }
    }

}
