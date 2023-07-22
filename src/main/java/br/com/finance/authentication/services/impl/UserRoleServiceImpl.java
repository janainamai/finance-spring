package br.com.finance.authentication.services.impl;

import br.com.finance.authentication.domain.entities.RoleEntity;
import br.com.finance.authentication.domain.entities.UserEntity;
import br.com.finance.authentication.infra.exception.BadRequestException;
import br.com.finance.authentication.repositories.RoleRepository;
import br.com.finance.authentication.repositories.UserRepository;
import br.com.finance.authentication.services.UserRoleService;
import br.com.finance.authentication.services.dto.CreateUserRoleDto;
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
    public void saveUserRole(CreateUserRoleDto dto) {
        UserEntity user = getUser(dto);
        List<RoleEntity> roles = getRoles(dto);

        user.setRoles(roles);
        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleEntity> findAll() {
        return roleRepository.findAll();
    }

    @Override
    @Transactional
    public RoleEntity getRoleByName(String name) {
        return roleRepository.findByName(name)
                .orElseThrow(() -> new BadRequestException("Role not found with name: " + name));
    }

    private List<RoleEntity> getRoles(CreateUserRoleDto dto) {
        return dto.getRoleIds()
                .stream()
                .map(this::getRoleOrThrowException)
                .collect(toList());
    }

    private RoleEntity getRoleOrThrowException(UUID id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Role not found with id ".concat(id.toString())));
    }

    private UserEntity getUser(CreateUserRoleDto dto) {
        return userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new BadRequestException("The user was not found"));
    }

}
