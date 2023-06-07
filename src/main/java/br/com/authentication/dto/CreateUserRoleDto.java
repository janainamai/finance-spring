package br.com.authentication.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.UUID;

@Data
public class CreateUserRoleDto {

    @NotBlank(message = "idUser é obrigatório")
    private UUID idUser;

    @NotEmpty(message = "roleIds é obrigatório")
    private List<UUID> roleIds;

}
