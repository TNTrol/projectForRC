package ru.redcollar.store.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.redcollar.store.entity.Offer;

import java.util.List;


public interface OfferRepository extends JpaRepository<Offer, Long>, JpaSpecificationExecutor<Offer> {

    List<Offer> findByUserId(Long user_id, Pageable pageable);

    List<Offer> findByUserLogin(String login, Pageable pageable);

    @Query("SELECT o.id FROM Offer o " +
            "WHERE o.user.id=(:id)")
    List<Long> findAllIdsByUserIdWithPagination(@Param("id") Long id, Pageable pageable);

    @Query("SELECT o FROM Offer o " +
            "LEFT JOIN FETCH o.products " +
            "WHERE o.id IN (:ids)")
    List<Offer> findAllOffer(@Param("ids") List<Long> ids);
}
