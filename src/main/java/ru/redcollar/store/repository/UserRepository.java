package ru.redcollar.store.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.redcollar.store.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByLogin(String login);

    boolean existsByLogin(String login);

    boolean existsByLoginOrEmail(String login, String email);

    boolean existsByEmail(String email);

    @Query("SELECT u FROM User u " +
            "LEFT JOIN FETCH u.roles " +
            "WHERE u.id IN (:ids)")
    List<User> findAllUser(@Param("ids") List<Long> ids);

    @Query("SELECT u.id FROM User u")
    List<Long> findAllIdsWithPagination(Pageable pageable);

    @Query("SELECT u.id FROM User u " +
            "WHERE u.login = :login")
    Long findIdByLogin(@Param("login") String login);
}
