package ru.redcollar.store.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.redcollar.store.domain.entity.Offer;
import ru.redcollar.store.domain.entity.User;

import java.util.List;

public interface OfferRepository extends JpaRepository<Offer, Long> {

    List<Offer> findByUserId(Long user_id, Pageable pageable);

    List<Offer> findByUserLogin(String login, Pageable pageable);

    @Query(value = "SELECT o.id FROM Offer o WHERE o.user.id=(:id)")
    List<Long> findAllIdsByUserIdWithPagination(@Param("id") Long id, Pageable pageable);

    @Query(value = "SELECT o FROM Offer o LEFT JOIN FETCH o.products WHERE o.id IN (:ids)")
    List<Offer> findAllOffer(@Param("ids") List<Long> ids);
}
