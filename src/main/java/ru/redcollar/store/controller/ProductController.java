package ru.redcollar.store.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.redcollar.store.domain.model.ProductDto;
import ru.redcollar.store.service.ProductService;

import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/list")
    public ResponseEntity<List> getAllProduct(@RequestParam(name = "page") @Min(0) int page, @RequestParam(name = "size") @Min(1) int size) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getAll(page, size));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<Void> createProduct(@RequestBody ProductDto product) {
        productService.saveProduct(product);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping
    public ResponseEntity<Void> updateProduct(@RequestBody ProductDto product) {
        productService.updateProduct(product);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.ok().build();
    }
}
