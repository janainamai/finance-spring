package br.com.finance.authentication.services;

import br.com.finance.authentication.domain.entities.Role;
import br.com.finance.authentication.domain.entities.User;
import br.com.finance.authentication.domain.dto.CreateUserRoleDto;

import java.util.List;

public interface UserRoleService {

    User saveUserRole(CreateUserRoleDto dto);

    List<Role> findAll();
}
