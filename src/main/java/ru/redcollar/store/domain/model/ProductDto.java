package ru.redcollar.store.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.redcollar.store.domain.entity.TypeProduct;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private Long id;
    private String description;
    private String name;
    private TypeProduct type;
    private BigDecimal cost;
}
