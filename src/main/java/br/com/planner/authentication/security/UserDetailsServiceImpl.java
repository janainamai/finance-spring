package br.com.planner.authentication.security;

import br.com.planner.authentication.domain.UserAccount;
import br.com.planner.authentication.exception.BadRequestException;
import br.com.planner.authentication.repository.UserRepository;
import br.com.planner.authentication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService service;
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccount user = userRepository.findByUsernameFetchRoles(username);

        if (Objects.isNull(user)) {
            throw new BadRequestException("Usuário não existe");
        }

        return CustomUserDetails.create(user);
    }

}
