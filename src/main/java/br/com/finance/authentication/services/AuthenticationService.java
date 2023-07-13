package br.com.finance.authentication.services;

import br.com.finance.authentication.services.dto.LoginDto;
import br.com.finance.authentication.services.dto.RegisterUserDto;

public interface AuthenticationService {

    void register(RegisterUserDto dto);

    String login(LoginDto dto);
}
