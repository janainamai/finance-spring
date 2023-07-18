package br.com.finance.authentication.impl;

import br.com.finance.authentication.domain.entities.RoleEntity;
import br.com.finance.authentication.domain.entities.UserEntity;
import br.com.finance.authentication.infra.exception.BadRequestException;
import br.com.finance.authentication.repositories.RoleRepository;
import br.com.finance.authentication.repositories.UserRepository;
import br.com.finance.authentication.services.dto.CreateUserRoleDto;
import br.com.finance.authentication.services.impl.UserRoleServiceImpl;
import br.com.finance.authentication.utils.RoleConstants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserRoleServiceImplTest {

    @InjectMocks
    private UserRoleServiceImpl service;
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Captor
    private ArgumentCaptor<UserEntity> captorUser;

    @Test
    void testSaveUserRoleWhenUserAndRolesWasFound() {
        UUID userId = UUID.randomUUID();
        UUID roleUserId = UUID.randomUUID();
        UUID roleAdminId = UUID.randomUUID();
        List<UUID> rolesIds = List.of(roleUserId, roleAdminId);

        CreateUserRoleDto dto = new CreateUserRoleDto();
        dto.setUserId(userId);
        dto.setRoleIds(rolesIds);

        UserEntity userFound = new UserEntity();
        userFound.setId(userId);
        userFound.setLogin("janainamai");
        when(userRepository.findById(dto.getUserId())).thenReturn(Optional.of(userFound));

        RoleEntity roleUser = createRole("USER");
        RoleEntity roleAdmin = createRole("ADMIN");
        List<RoleEntity> roles = List.of(roleAdmin, roleUser);
        when(roleRepository.findById(roleUserId)).thenReturn(Optional.of(roleUser));
        when(roleRepository.findById(roleAdminId)).thenReturn(Optional.of(roleAdmin));

        service.saveUserRole(dto);

        verify(userRepository).save(captorUser.capture());

        UserEntity savedUser = captorUser.getValue();
        assertThat(savedUser.getId()).isEqualTo(userFound.getId());
        assertThat(savedUser.getUsername()).isEqualTo(userFound.getUsername());
        assertThat(savedUser.getRoles()).containsAll(roles);
    }

    @Test
    void testSaveUserRoleWhenUserIsNotFound() {
        UUID userId = UUID.randomUUID();
        UUID roleUserId = UUID.randomUUID();
        UUID roleAdminId = UUID.randomUUID();
        List<UUID> roleIds = List.of(roleUserId, roleAdminId);

        CreateUserRoleDto dto = new CreateUserRoleDto();
        dto.setUserId(userId);
        dto.setRoleIds(roleIds);

        when(userRepository.findById(dto.getUserId())).thenReturn(Optional.empty());

        assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> service.saveUserRole(dto))
                .withMessage("The user was not found");
    }

    @Test
    void testSaveUserRoleWhenRolesWasNotFound() {
        UUID userId = UUID.randomUUID();
        UUID roleId = UUID.randomUUID();

        CreateUserRoleDto dto = new CreateUserRoleDto();
        dto.setUserId(userId);
        dto.setRoleIds(List.of(roleId));

        when(userRepository.findById(dto.getUserId())).thenReturn(Optional.of(new UserEntity()));
        when(roleRepository.findById(roleId)).thenReturn(Optional.empty());

        assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> service.saveUserRole(dto))
                .withMessage("Role not found with id ".concat(roleId.toString()));
    }

    @Test
    void testFindAll() {
        List<RoleEntity> rolesFound = List.of(createRole("USER"), createRole("ADMIN"));
        when(roleRepository.findAll()).thenReturn(rolesFound);

        List<RoleEntity> roles = service.findAll();
        assertThat(roles).isEqualTo(rolesFound);
    }

    @Test
    void testGetRoleByNameWhenItFound() {
        RoleEntity adminRole = new RoleEntity();
        adminRole.setName(RoleConstants.ADMIN);

        when(roleRepository.findByName(RoleConstants.ADMIN)).thenReturn(Optional.of(adminRole));

        RoleEntity role = service.getRoleByName(RoleConstants.ADMIN);
        assertThat(role.getName()).isEqualTo(RoleConstants.ADMIN);
    }

    @Test
    void testGetRoleByNameWhenItNotFound() {
        when(roleRepository.findByName(RoleConstants.ADMIN)).thenReturn(Optional.empty());

        assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> service.getRoleByName(RoleConstants.ADMIN))
                .withMessage("Role not found with name: ADMIN");
    }

    private static RoleEntity createRole(String name) {
        RoleEntity roleUser = new RoleEntity();
        roleUser.setName(name);
        return roleUser;
    }

}
