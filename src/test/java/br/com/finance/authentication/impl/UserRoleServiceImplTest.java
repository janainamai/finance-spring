package br.com.finance.authentication.impl;

import br.com.finance.authentication.domain.dto.CreateUserRoleDto;
import br.com.finance.authentication.domain.entities.Role;
import br.com.finance.authentication.domain.entities.User;
import br.com.finance.authentication.infra.exception.BadRequestException;
import br.com.finance.authentication.repositories.RoleRepository;
import br.com.finance.authentication.repositories.UserRepository;
import br.com.finance.authentication.services.impl.UserRoleServiceImpl;
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
    private ArgumentCaptor<User> captorUser;

    @Test
    void testSaveUserRoleWhenUserAndRolesWasFound() {
        UUID userId = UUID.randomUUID();
        UUID roleUserId = UUID.randomUUID();
        UUID roleAdminId = UUID.randomUUID();

        CreateUserRoleDto createUserRoleDto = new CreateUserRoleDto(userId, List.of(roleUserId, roleAdminId));

        User userFound = new User();
        userFound.setId(userId);
        userFound.setLogin("janainamai");
        when(userRepository.findById(createUserRoleDto.idUser())).thenReturn(Optional.of(userFound));

        Role roleUser = createRole("USER");
        Role roleAdmin = createRole("ADMIN");
        when(roleRepository.findById(roleUserId)).thenReturn(Optional.of(roleUser));
        when(roleRepository.findById(roleAdminId)).thenReturn(Optional.of(roleAdmin));

        User user = service.saveUserRole(createUserRoleDto);

        assertThat(user.getRoles()).containsAll(List.of(roleAdmin, roleUser));
        assertThat(user.getLogin()).isEqualTo(userFound.getUsername());
        verify(userRepository).save(captorUser.capture());

        User savedUser = captorUser.getValue();
        assertThat(savedUser.getId()).isEqualTo(userFound.getId());
        assertThat(savedUser.getUsername()).isEqualTo(userFound.getUsername());
        assertThat(savedUser.getRoles()).containsAll(List.of(roleUser, roleAdmin));
    }

    @Test
    void testSaveUserRoleWhenUserIsNotFound() {
        UUID userId = UUID.randomUUID();
        UUID roleUserId = UUID.randomUUID();
        UUID roleAdminId = UUID.randomUUID();

        CreateUserRoleDto createUserRoleDto = new CreateUserRoleDto(userId, List.of(roleUserId, roleAdminId));

        when(userRepository.findById(createUserRoleDto.idUser())).thenReturn(Optional.empty());

        assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> service.saveUserRole(createUserRoleDto))
                .withMessage("The user was not found");
    }

    @Test
    void testSaveUserRoleWhenRolesWasNotFound() {
        UUID userId = UUID.randomUUID();
        UUID roleUserId = UUID.randomUUID();

        CreateUserRoleDto createUserRoleDto = new CreateUserRoleDto(userId, List.of(roleUserId));

        when(userRepository.findById(createUserRoleDto.idUser())).thenReturn(Optional.of(new User()));
        when(roleRepository.findById(roleUserId)).thenReturn(Optional.empty());

        assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> service.saveUserRole(createUserRoleDto))
                .withMessage("Role not found with id ".concat(roleUserId.toString()));
    }

    @Test
    void testFindAll() {
        List<Role> rolesFound = List.of(createRole("USER"), createRole("ADMIN"));
        when(roleRepository.findAll()).thenReturn(rolesFound);

        List<Role> roles = service.findAll();
        assertThat(roles).isEqualTo(rolesFound);
    }

    private static Role createRole(String name) {
        Role roleUser = new Role();
        roleUser.setName(name);
        return roleUser;
    }

}
