package br.com.finance.authentication.services.impl;

import br.com.finance.authentication.domain.entities.Role;
import br.com.finance.authentication.domain.entities.User;
import br.com.finance.authentication.domain.dto.AuthenticationDto;
import br.com.finance.authentication.domain.dto.RegisterUserDto;
import br.com.finance.authentication.repositories.UserRepository;
import br.com.finance.authentication.services.EncoderService;
import br.com.finance.authentication.services.AuthenticationService;
import br.com.finance.authentication.utils.RoleConstants;
import br.com.finance.authentication.validators.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private UserValidator validator;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private EncoderService encoder;

    @Override
    @Transactional
    public void register(RegisterUserDto input) {
        validator.validateSamePassword(input.login(), input.confirmPassword());
        validator.validateUsernameAlreadyExists(input.login());

        User user = input.toUser();
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRoles(List.of(new Role(RoleConstants.FREE_USER)));

        repository.save(user);
    }

    @Override
    public void authenticate(AuthenticationDto input) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(input.login(), input.password());
        authenticationManager.authenticate(usernamePassword);
    }

}
