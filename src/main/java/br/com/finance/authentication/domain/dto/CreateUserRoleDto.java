package br.com.finance.authentication.domain.dto;

import java.util.List;
import java.util.UUID;

public record CreateUserRoleDto(UUID idUser, List<UUID> roleIds) {

}
