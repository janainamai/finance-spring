package br.com.finance.authentication.infra.security.intefaces;

import br.com.finance.authentication.domain.entities.UserEntity;

public interface TokenService {
    String generateToken(UserEntity user);

    String validateToken(String token);
}
