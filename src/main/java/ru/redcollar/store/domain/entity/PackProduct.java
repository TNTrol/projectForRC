package ru.redcollar.store.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pack_product")
public class PackProduct implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    private Offer offer;

    @Column(name = "count")
    private Integer count;

    public String toString() {
        return "";
    }
}
