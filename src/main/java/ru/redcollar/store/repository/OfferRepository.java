package ru.redcollar.store.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.redcollar.store.domain.entity.Offer;
import ru.redcollar.store.domain.entity.User;

import java.util.List;

public interface OfferRepository extends JpaRepository<Offer, Long> {

    List<Offer> findByUserId(Long user_id, Pageable pageable);

    List<Offer> findByUserLogin(String login, Pageable pageable);
}
