package ru.redcollar.store.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.redcollar.store.domain.model.OfferDto;
import ru.redcollar.store.service.OfferService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/offer")
public class OfferController {

    private final OfferService offerService;

    @GetMapping("/history")
    public ResponseEntity<List> getAllOffer(@RequestParam(name = "page") int page, @RequestParam(name = "size") int size) {
        return ResponseEntity.ok(offerService.getAllOffer(page, size));
    }

    @PostMapping
    public ResponseEntity<Void> createOffer(@RequestBody OfferDto offer) {
        offerService.saveOffer(offer);
        return ResponseEntity.ok().build();
    }

}
