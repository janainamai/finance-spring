package br.com.finance.authentication.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class CreateUserRoleDto {

    @NotNull(message = "idUser é obrigatório")
    private UUID idUser;

    @NotEmpty(message = "roleIds é obrigatório")
    private List<UUID> roleIds;

}
