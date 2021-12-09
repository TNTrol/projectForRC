package ru.redcollar.store.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.redcollar.store.domain.entity.TypeProduct;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewProduct {
    private String description;
    private String name;
    private TypeProduct type;
    private Double cost;
}
