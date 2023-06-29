package br.com.finance.authentication.dto;

import br.com.finance.authentication.domain.UserAccount;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterUserDto {

    @NotBlank(message = "Login é obrigatório")
    private String login;

    @NotBlank(message = "E-mail é obrigatório")
    @Email(message = "E-mail inválido")
    private String email;

    @NotBlank(message = "Senha é obrigatória")
    private String password;

    @NotBlank(message = "Confirmação de senha é obrigatória")
    private String confirmPassword;

    private String phone1;

    private String phone2;

    public UserAccount toEntity() {
        UserAccount userAccount = new UserAccount();
        userAccount.setLogin(this.login);
        userAccount.setEmail(this.email);
        userAccount.setPassword(this.email);

        return userAccount;
    }

}
