package ru.redcollar.store.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.redcollar.store.entity.TypeProduct;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class  ProductPageableCriteriaDto extends PageDto{

    private String name;
    private TypeProduct type;
    private BigDecimal lowerCost;
    private BigDecimal upperCost;
}
