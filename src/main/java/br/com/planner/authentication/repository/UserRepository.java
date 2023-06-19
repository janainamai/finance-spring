package br.com.planner.authentication.repository;

import br.com.planner.authentication.domain.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserAccount, UUID> {

    Optional<UserAccount> findByUsername(String username);

    boolean existsByUsername(String username);

    @Query("SELECT u FROM UserAccount u JOIN FETCH u.roles where u.username = :username")
    UserAccount findByUsernameFetchRoles(@Param("username") String username);

}
