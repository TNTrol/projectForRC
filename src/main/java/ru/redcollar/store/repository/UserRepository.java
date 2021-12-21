package ru.redcollar.store.repository;

import org.springframework.data.jpa.repository.Query;
import ru.redcollar.store.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByLogin(String login);

    boolean existsByLogin(String login);

    boolean existsByLoginOrEmail(String login, String email);

    boolean existsByEmail(String email);

    @Query("SELECT p FROM User p LEFT JOIN FETCH p.roles")
    List<User> findWithoutNPlusOne();
}
