package ru.redcollar.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.redcollar.store.entity.PackProduct;

import java.util.List;

public interface PackProductRepository extends JpaRepository<PackProduct, Long> {

    @Query("SELECT p FROM PackProduct p " +
            "JOIN FETCH p.product " +
            "WHERE p.offer.id IN (:ids) " +
            "ORDER BY p.offer.id")
    List<PackProduct> findAllPackProductByOfferIds(@Param("ids") List<Long> ids);

}
