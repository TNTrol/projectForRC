package ru.redcollar.store.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.redcollar.store.domain.entity.TypeProduct;
import ru.redcollar.store.validator.OnCreateOffer;
import ru.redcollar.store.validator.OnCreateProduct;
import ru.redcollar.store.validator.OnUpdateProduct;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    @Null(groups = OnCreateProduct.class)
    @NotNull(groups = {OnUpdateProduct.class, OnCreateOffer.class})
    private Long id;
    @NotNull(groups = {OnCreateProduct.class, OnUpdateProduct.class})
    private String description;
    @NotNull(groups = {OnCreateProduct.class, OnUpdateProduct.class})
    private String name;
    @NotNull(groups = {OnCreateProduct.class, OnUpdateProduct.class})
    private TypeProduct type;
    @NotNull(groups = {OnCreateProduct.class, OnUpdateProduct.class})
    private BigDecimal cost;
}
