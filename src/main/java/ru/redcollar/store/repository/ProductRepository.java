package ru.redcollar.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.redcollar.store.domain.entity.PackProduct;
import ru.redcollar.store.domain.entity.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsByName(String name);

    @Query("SELECT p FROM Product p WHERE p.id IN (:ids)")
    List<Product> findByIds(@Param("ids") List<Long> ids);

    @Query(value = "SELECT p FROM PackProduct p LEFT JOIN FETCH p.product WHERE p.offer.id IN (:ids) ORDER BY p.offer.id")
    List<PackProduct> findAllPackProductByOfferIds(@Param("ids") List<Long> ids);
}
