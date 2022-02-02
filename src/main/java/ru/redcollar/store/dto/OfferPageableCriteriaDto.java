package ru.redcollar.store.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Null;
import java.math.BigDecimal;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfferPageableCriteriaDto extends PageDto {

    private String productName;
    @Null
    private String login;
    private Instant with;
    private Instant to;
    private BigDecimal lowerCost;
    private BigDecimal upperCost;
}
