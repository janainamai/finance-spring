package br.com.finance.authentication.controllers.client.input;

import br.com.finance.authentication.services.dto.LoginDto;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginInput {

    @NotBlank(message = "login is required")
    private String login;

    @NotBlank(message = "password is required")
    private String password;

    public LoginDto toDto() {
        LoginDto dto = new LoginDto();
        dto.setLogin(login);
        dto.setPassword(password);

        return dto;
    }

}
