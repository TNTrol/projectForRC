package ru.redcollar.store.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.redcollar.store.domain.model.OfferDto;
import ru.redcollar.store.service.OfferService;
import ru.redcollar.store.validator.OnCreateOffer;
import ru.redcollar.store.validator.OnCreateProduct;
import ru.redcollar.store.validator.OnUpdateProduct;

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
    public ResponseEntity<List> getAllOffer(@RequestParam(name = "page") @Min(0) int page, @RequestParam(name = "size")  @Min(1) int size) {
        return ResponseEntity.ok(offerService.getAllOffer(page, size));
    }

    @PostMapping
    @Validated(OnCreateOffer.class)
    public ResponseEntity<Void> createOffer(@Valid @RequestBody OfferDto offer) {
        offerService.saveOffer(offer);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{offerId}")
    public ResponseEntity<Void> send(@PathVariable @Min(1) Long offerId) {
        offerService.sendOffer(offerId);
        return ResponseEntity.ok().build();
    }

}
