package br.com.finance.authentication.service;

import br.com.finance.authentication.domain.UserAccount;
import br.com.finance.authentication.dto.RegisterUserDto;

import java.util.Optional;

public interface UserService {

    void register(RegisterUserDto userAccount);

}
