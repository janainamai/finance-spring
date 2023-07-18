package br.com.finance.authentication.repositories;

import br.com.finance.authentication.domain.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    @Query("SELECT u FROM users u JOIN FETCH u.roles WHERE u.login = :login")
    UserDetails findByLogin(@Param("login") String login);

    boolean existsByLogin(String username);

}
