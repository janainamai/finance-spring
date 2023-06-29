package br.com.finance.authentication.service;

import br.com.finance.authentication.domain.Role;
import br.com.finance.authentication.dto.CreateUserRoleDto;
import br.com.finance.authentication.dto.UserRoleCreatedDto;

import java.util.List;

public interface UserRoleService {

    UserRoleCreatedDto saveUserRole(CreateUserRoleDto dto);

    List<Role> findAll();
}
