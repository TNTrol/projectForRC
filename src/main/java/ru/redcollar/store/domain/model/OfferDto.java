package ru.redcollar.store.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.redcollar.store.domain.entity.StatusOffer;

import java.sql.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfferDto {

    private Long id;
    private Double cost;
    private Date date;
    private StatusOffer status;
    private List<ProductDto> products;
}
