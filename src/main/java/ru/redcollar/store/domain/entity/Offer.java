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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "cost")
    private BigDecimal cost;

    @Column(name = "date")
    private Instant date;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusOffer status;

    @OneToMany(mappedBy = "offer", cascade = CascadeType.ALL)
    private List<PacProduct> products;

    @PrePersist
    private void prePersist() {
        products.forEach( c -> c.setOffer(this));
    }

    public Offer(Long id, BigDecimal cost, Instant date, StatusOffer status, List<PacProduct> products){
        this.products = products;
        this.id = id;
        this.cost = cost;
        this.date = date;
        this.status = status;
    }

}
