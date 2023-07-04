package br.com.finance.authentication.services.dto;

import lombok.Data;

@Data
public class RegisterUserDto {

    private String login;

    private String email;

    private String password;

    private String confirmPassword;

}
