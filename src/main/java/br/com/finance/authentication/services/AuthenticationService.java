package br.com.finance.authentication.services;

import br.com.finance.authentication.domain.dto.AuthenticationDto;
import br.com.finance.authentication.domain.dto.RegisterUserDto;

public interface AuthenticationService {

    void register(RegisterUserDto user);

    void authenticate(AuthenticationDto input);
}
