package ru.redcollar.store.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryDto implements Serializable {

    private String fromAddress;
    private String toAddress;
    private Long userId;
    private List<DeliveryProductDto> products;
}
