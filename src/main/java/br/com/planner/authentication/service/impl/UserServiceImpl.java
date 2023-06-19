package br.com.planner.authentication.service.impl;

import br.com.planner.authentication.domain.UserAccount;
import br.com.planner.authentication.dto.RegisterUserDto;
import br.com.planner.authentication.repository.UserRepository;
import br.com.planner.authentication.service.EncoderService;
import br.com.planner.authentication.service.UserService;
import br.com.planner.authentication.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private UserValidator validator;
    @Autowired
    private EncoderService encoder;

    @Override
    @Transactional
    public void registerUserAccount(RegisterUserDto dto) {
        validator.validateSamePassword(dto.getPassword(), dto.getConfirmPassword());
        validator.validateUsernameAlreadyExists(dto.getUsername());

        UserAccount userAccount = dto.toEntity();
        String hashPassword = encoder.encode(userAccount.getPassword());
        userAccount.setPassword(hashPassword);

        repository.save(userAccount);
    }

    @Override
    public Optional<UserAccount> getByUsername(String username) {
        return repository.findByUsername(username);
    }

}
