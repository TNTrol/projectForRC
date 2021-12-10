package ru.redcollar.store.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.redcollar.store.domain.entity.StatusOffer;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfferDto {

    private Long id;
    private BigDecimal cost;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Instant date;
    private StatusOffer status;
    private List<ProductDto> products;
}
