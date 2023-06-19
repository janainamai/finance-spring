package br.com.planner.authentication.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Data
public class CreateUserRoleDto {

    @NotNull(message = "idUser é obrigatório")
    private UUID idUser;

    @NotEmpty(message = "roleIds é obrigatório")
    private List<UUID> roleIds;

}
