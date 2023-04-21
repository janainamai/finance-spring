package br.com.authentication.service;

import br.com.authentication.domain.UserAccount;

import java.util.Optional;

public interface UserService {

    void registerUserAccount(UserAccount userAccount);

    String authenticate(String email, String password);

    Optional<UserAccount> getUserByEmail(String email);

}
