package ru.redcollar.store.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "offer_to_product")
public class PackProduct implements Serializable {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    private Offer offer;

    @Column(name = "count")
    private Integer count;

    public PackProduct(Integer count, Long idProduct, String s, String name, TypeProduct type, BigDecimal cost, Long idOffer) {
        this.count = count;
        this.product = new Product(idProduct, s, name, type, cost);
        this.offer = new Offer();
        this.offer.setId(idOffer);
    }

    public String toString() {
        return "";
    }
}
