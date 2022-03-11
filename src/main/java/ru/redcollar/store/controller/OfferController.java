package ru.redcollar.store.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.redcollar.store.dto.OfferDto;
import ru.redcollar.store.dto.OfferPageableCriteriaDto;
import ru.redcollar.store.service.OfferService;
import ru.redcollar.store.validator.OnCreateOffer;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/offer")
@Validated
public class OfferController {

    private final OfferService offerService;

    @PostMapping("/history")
    @Operation(summary = "Getting a order history")
    @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = OfferDto.class))))
    public ResponseEntity<List<OfferDto>> getAllOffer(@Parameter(description = "Offer criteria with pageable data", required = true) @Valid @RequestBody OfferPageableCriteriaDto criteriaDto) {
        return ResponseEntity.ok(offerService.getAllOffer(criteriaDto));
    }

    @PostMapping
    @Validated(OnCreateOffer.class)
    @Operation(summary = "New offer creation")
    public ResponseEntity<OfferDto> createOffer(@Parameter(description = "new offer", required = true) @Valid @RequestBody OfferDto offer) {
        return ResponseEntity.ok(offerService.saveOffer(offer));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{offerId}")
    @Operation(summary = "Send offer to customer")
    @ApiResponse(responseCode = "404", description = "Product don't exist!", content = @Content)
    public ResponseEntity<Void> send(@Parameter(description = "Id of offer") @PathVariable @Min(1) Long offerId) {
        offerService.sendOffer(offerId);
        return ResponseEntity.ok().build();
    }

}
