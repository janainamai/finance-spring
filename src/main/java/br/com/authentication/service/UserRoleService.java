package br.com.authentication.service;

import br.com.authentication.domain.Role;
import br.com.authentication.dto.CreateUserRoleDto;
import br.com.authentication.dto.UserRoleCreatedDto;

import java.util.List;

public interface UserRoleService {

    UserRoleCreatedDto saveUserRole(CreateUserRoleDto dto);

    List<Role> findAll();
}
