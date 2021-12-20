package ru.redcollar.store.repository;

import ru.redcollar.store.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByLogin(String login);

    boolean existsByLogin(String login);

    boolean existsByLoginOrEmail(String login, String email);

    boolean existsByEmail(String email);
}
