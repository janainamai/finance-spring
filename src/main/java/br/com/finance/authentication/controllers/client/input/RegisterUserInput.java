package br.com.finance.authentication.controllers.client.input;

import br.com.finance.authentication.services.dto.RegisterUserDto;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterUserInput {

    @NotBlank(message = "login is required")
    private String login;

    @NotBlank(message = "email is required")
    private String email;

    @NotBlank(message = "password is required")
    private String password;

    @NotBlank(message = "confirmPassword is required")
    private String confirmPassword;

    public RegisterUserDto toDto() {
        RegisterUserDto dto = new RegisterUserDto();
        dto.setLogin(login);
        dto.setEmail(email);
        dto.setPassword(password);
        dto.setConfirmPassword(confirmPassword);

        return dto;
    }

}
