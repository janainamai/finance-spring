package br.com.finance.authentication.repositories;

import br.com.finance.authentication.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    UserDetails findByLogin(String username);

    boolean existsByLogin(String username);

}
