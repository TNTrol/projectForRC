package ru.redcollar.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.redcollar.store.domain.entity.Product;
import ru.redcollar.store.domain.entity.Role;
import ru.redcollar.store.domain.entity.TypeProduct;

public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsByName(String name);
}
