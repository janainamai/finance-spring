package br.com.authentication.dto;

import br.com.authentication.domain.UserAccount;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserDto {

    @NotBlank(message = "Nome é obrigatório")
    private String firstName;

    @NotBlank(message = "Sobrenome é obrigatório")
    private String lastName;

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
                .email(this.email)
                .password(this.password)
                .birthdate(this.birthdate)
                .phone1(this.phone1)
                .phone2(this.phone2)
                .build();
    }

}
