package ru.redcollar.store.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "offer")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "cost")
    private BigDecimal cost;

    @Column(name = "date")
    private Instant date;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusOffer status;

    @ManyToMany
    @JoinTable(
            name = "offer_to_product",
            joinColumns = @JoinColumn(name = "offer_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products;

}
