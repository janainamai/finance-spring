package br.com.planner.authentication.service;

import br.com.planner.authentication.domain.Role;
import br.com.planner.authentication.dto.CreateUserRoleDto;
import br.com.planner.authentication.dto.UserRoleCreatedDto;

import java.util.List;

public interface UserRoleService {

    UserRoleCreatedDto saveUserRole(CreateUserRoleDto dto);

    List<Role> findAll();
}
