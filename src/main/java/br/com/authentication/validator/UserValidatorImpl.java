package br.com.authentication.validator;

import br.com.authentication.exception.BadRequestException;
import br.com.authentication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserValidatorImpl implements UserValidator {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void validateSamePassword(String password1, String password2) {
        if (!password1.equals(password2)) {
            throw new BadRequestException("As senhas informadas não coincidem");
        }
    }

    @Override
    public void validateUsernameAlreadyExists(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new BadRequestException("Já existe um usuário com esse username");
        }
    }

}
