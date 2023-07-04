package br.com.finance.authentication.services;

import br.com.finance.authentication.domain.entities.RoleEntity;
import br.com.finance.authentication.services.dto.CreateUserRoleDto;

import java.util.List;

public interface UserRoleService {

    void saveUserRole(CreateUserRoleDto dto);

    List<RoleEntity> findAll();
}
