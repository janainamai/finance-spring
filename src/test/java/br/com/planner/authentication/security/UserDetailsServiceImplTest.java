package br.com.planner.authentication.security;

import br.com.planner.authentication.domain.Role;
import br.com.planner.authentication.domain.UserAccount;
import br.com.planner.authentication.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

    @InjectMocks
    private UserDetailsServiceImpl service;
    @Mock
    private UserRepository userRepository;

    @Test
    void testLoadUserByUsername() {
        String username = "janainamai";

        UserAccount userAccount = new UserAccount();
        userAccount.setFirstName("Janaina");
        userAccount.setFirstName("Mai");
        userAccount.setUsername(username);
        userAccount.setEmail("janaina@hotmail.com");
        userAccount.setPassword("password123");
        userAccount.setBirthdate(LocalDate.of(2000, 1, 1));
        userAccount.setPhone1("10 91234-5678");
        userAccount.setPhone1("99 98765-4321");
        userAccount.setRoles(List.of(createRole("USER")));
        when(userRepository.findByUsernameFetchRoles(username)).thenReturn(userAccount);

        UserDetails userDetails = service.loadUserByUsername(username);
        assertThat(userDetails.getUsername()).isEqualTo(userAccount.getUsername());
        assertThat(userDetails.getPassword()).isEqualTo(userAccount.getPassword());
        userDetails.getAuthorities().forEach(authority -> {
            assertThat(authority.getAuthority()).isEqualTo("ROLE_USER");
        });
    }

    private static Role createRole(String name) {
        Role roleUser = new Role();
        roleUser.setName(name);
        return roleUser;
    }

}