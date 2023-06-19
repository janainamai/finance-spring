package br.com.planner.authentication.dto;

import br.com.planner.authentication.domain.UserAccount;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Data
public class RegisterUserDto {

    @NotBlank(message = "Nome é obrigatório")
    private String firstName;

    @NotBlank(message = "Sobrenome é obrigatório")
    private String lastName;

    @NotBlank(message = "Username é obrigatório")
    private String username;

    @NotBlank(message = "E-mail é obrigatório")
    @Email(message = "E-mail inválido")
    private String email;

    @NotBlank(message = "Senha é obrigatória")
    private String password;

    @NotBlank(message = "Confirmação de senha é obrigatória")
    private String confirmPassword;

    @NotNull(message = "Data de nascimento é obrigatória")
    @Past(message = "Data de nascimento deve ser no passado")
    private LocalDate birthdate;

    private String phone1;

    private String phone2;

    public UserAccount toEntity() {
        return UserAccount.builder()
                .firstName(this.firstName)
                .lastName(this.lastName)
                .username(this.username)
                .email(this.email)
                .password(this.password)
                .birthdate(this.birthdate)
                .phone1(this.phone1)
                .phone2(this.phone2)
                .build();
    }

}
