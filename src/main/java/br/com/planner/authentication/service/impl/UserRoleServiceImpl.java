package br.com.planner.authentication.service.impl;

import br.com.planner.authentication.domain.Role;
import br.com.planner.authentication.domain.UserAccount;
import br.com.planner.authentication.dto.CreateUserRoleDto;
import br.com.planner.authentication.dto.UserRoleCreatedDto;
import br.com.planner.authentication.exception.BadRequestException;
import br.com.planner.authentication.repository.RoleRepository;
import br.com.planner.authentication.repository.UserRepository;
import br.com.planner.authentication.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    @Transactional
    public UserRoleCreatedDto saveUserRole(CreateUserRoleDto dto) {
        UserAccount userAccount = getUserAccount(dto);
        List<Role> roles = getRoles(dto);

        userAccount.setRoles(roles);
        userRepository.save(userAccount);

        UserRoleCreatedDto userRoleCreatedDto = new UserRoleCreatedDto();
        userRoleCreatedDto.setUsername(userAccount.getUsername());
        userRoleCreatedDto.setRoles(roles.stream().map(Role::getName).collect(toList()));

        return userRoleCreatedDto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    private List<Role> getRoles(CreateUserRoleDto dto) {
        return dto.getRoleIds()
                .stream()
                .map(this::getRoleOrThrowException)
                .collect(toList());
    }

    private Role getRoleOrThrowException(UUID id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Role not found with id ".concat(id.toString())));
    }

    private UserAccount getUserAccount(CreateUserRoleDto dto) {
        return userRepository.findById(dto.getIdUser())
                .orElseThrow(() -> new BadRequestException("The user was not found"));
    }

}
