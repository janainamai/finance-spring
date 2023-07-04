package br.com.finance.authentication.repositories;

import br.com.finance.authentication.domain.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    UserDetails findByLogin(String username);

    boolean existsByLogin(String username);

}
