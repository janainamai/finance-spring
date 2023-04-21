package br.com.authentication.service;

import br.com.authentication.domain.UserAccount;
import br.com.authentication.exception.BadRequestException;
import br.com.authentication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Override
    public void registerUserAccount(UserAccount userAccount) {
        String hashPassword = BCrypt.hashpw(userAccount.getPassword(), BCrypt.gensalt());

        userAccount.setPassword(hashPassword);

        repository.save(userAccount);
    }

    @Override
    public String authenticate(String email, String password) throws AuthenticationException {
        Optional<UserAccount> optionalUser = getUserByEmail(email);

        if (optionalUser.isEmpty()) {
            throw new BadRequestException("O email informado não possui cadastro no sistema");
        }

        if (!matchesPassword(password)) {
            throw new BadRequestException("Senha incorreta");
        }

        return "Token"; // ToDo
    }

    @Override
    public Optional<UserAccount> getUserByEmail(String email) {
        return repository.findByEmail(email);
    }

    private boolean matchesPassword(String password) {
        String hashPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        return BCrypt.checkpw(password, hashPassword);
    }

}
