package ru.redcollar.store.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.redcollar.store.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.stream.Stream;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByLogin(String login);

    boolean existsByLogin(String login);

    boolean existsByLoginOrEmail(String login, String email);

    boolean existsByEmail(String email);

    @Query(value = "SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.id IN (:ids)")
    List<User> findAllUser(@Param("ids") List<Long> ids);

    @Query(value = "SELECT u.id FROM User u")
    List<Long> findAllIdsWithPagination(Pageable pageable);
}
