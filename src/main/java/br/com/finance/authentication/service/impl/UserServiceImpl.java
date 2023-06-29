package br.com.finance.authentication.service.impl;

import br.com.finance.authentication.domain.UserAccount;
import br.com.finance.authentication.dto.RegisterUserDto;
import br.com.finance.authentication.repository.UserRepository;
import br.com.finance.authentication.service.EncoderService;
import br.com.finance.authentication.service.UserService;
import br.com.finance.authentication.validator.UserValidator;
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
    public void register(RegisterUserDto dto) {
        validator.validateSamePassword(dto.getPassword(), dto.getConfirmPassword());
        validator.validateUsernameAlreadyExists(dto.getLogin());

        UserAccount userAccount = dto.toEntity();
        String hashPassword = encoder.encode(userAccount.getPassword());
        userAccount.setPassword(hashPassword);

        repository.save(userAccount);
    }

}
