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
import ru.redcollar.store.domain.model.OfferDto;
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

    @GetMapping("/history")
    @Operation(summary = "Getting a order history")
    @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = OfferDto.class))))
    public ResponseEntity<List> getAllOffer(@Parameter(description = "Number of page", required = true) @RequestParam(name = "page") @Min(0) int page, @Parameter(description = "Size of page", required = true) @RequestParam(name = "size") @Min(1) int size) {
        return ResponseEntity.ok(offerService.getAllOffer(page, size));
    }

    @PostMapping
    @Validated(OnCreateOffer.class)
    @Operation(summary = "New offer creation")
    public ResponseEntity<Void> createOffer(@Parameter(description = "new offer", required = true) @Valid @RequestBody OfferDto offer) {
        offerService.saveOffer(offer);
        return ResponseEntity.ok().build();
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
