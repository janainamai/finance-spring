package br.com.finance.authentication.services.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class CreateUserRoleDto {

    private UUID userId;

    private List<UUID> roleIds;

}
