package br.com.finance.authentication.services.impl;

import br.com.finance.authentication.domain.entities.Role;
import br.com.finance.authentication.domain.entities.User;
import br.com.finance.authentication.domain.dto.CreateUserRoleDto;
import br.com.finance.authentication.infra.exception.BadRequestException;
import br.com.finance.authentication.repositories.RoleRepository;
import br.com.finance.authentication.repositories.UserRepository;
import br.com.finance.authentication.services.UserRoleService;
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
    public User saveUserRole(CreateUserRoleDto dto) {
        User user = getUser(dto);
        List<Role> roles = getRoles(dto);

        user.setRoles(roles);
        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    private List<Role> getRoles(CreateUserRoleDto dto) {
        return dto.roleIds()
                .stream()
                .map(this::getRoleOrThrowException)
                .collect(toList());
    }

    private Role getRoleOrThrowException(UUID id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Role not found with id ".concat(id.toString())));
    }

    private User getUser(CreateUserRoleDto dto) {
        return userRepository.findById(dto.idUser())
                .orElseThrow(() -> new BadRequestException("The user was not found"));
    }

}
