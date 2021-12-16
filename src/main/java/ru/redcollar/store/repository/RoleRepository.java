package ru.redcollar.store.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.redcollar.store.domain.entity.Product;
import ru.redcollar.store.domain.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);

    @Query("SELECT p FROM Role p WHERE p.id IN (:ids)")
    List<Role> findByIds(@Param("ids")List<Long> ids);
}
