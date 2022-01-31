package ru.redcollar.store.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PackProductDto {

    private ProductDto product;
    private Integer count;
}
