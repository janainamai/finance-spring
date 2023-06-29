package br.com.planner.authentication.service;

import br.com.planner.authentication.domain.UserAccount;
import br.com.planner.authentication.dto.RegisterUserDto;

import java.util.Optional;

public interface UserService {

    void register(RegisterUserDto userAccount);

    Optional<UserAccount> getByUsername(String username);

}
