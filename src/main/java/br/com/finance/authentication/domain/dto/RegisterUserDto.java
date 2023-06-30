package br.com.finance.authentication.domain.dto;

import br.com.finance.authentication.domain.entities.User;

public record RegisterUserDto(String login, String email, String password, String confirmPassword) {

    public User toUser() {
        User user = new User();
        user.setLogin(this.login);
        user.setEmail(this.email);
        user.setPassword(this.email);

        return user;
    }

}
