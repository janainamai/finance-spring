package br.com.finance.authentication.controllers.client.input;

import br.com.finance.authentication.services.dto.CreateUserRoleDto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class CreateUserRoleInput {

    @NotNull(message = "idUser is required")
    private UUID idUser;

    @NotEmpty(message = "roleIds is required")
    private List<UUID> roleIds;

    public CreateUserRoleDto toDto() {
        CreateUserRoleDto dto = new CreateUserRoleDto();
        dto.setUserId(idUser);
        dto.setRoleIds(roleIds);

        return dto;
    }

}
