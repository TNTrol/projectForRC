package ru.redcollar.store.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.redcollar.store.dto.ProductPageableCriteriaDto;
import ru.redcollar.store.dto.ProductDto;
import ru.redcollar.store.service.ProductService;
import ru.redcollar.store.validator.OnCreateProduct;
import ru.redcollar.store.validator.OnUpdateProduct;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@Validated
public class ProductController {

    private final ProductService productService;

    @PostMapping("/list")
    @Operation(summary = "Get all production by criteria with pageable")
    @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ProductDto.class))))
    public ResponseEntity<List<ProductDto>> getAllProductByCriteria(@Parameter(description = "Product criteria with pageable data", required = true) @Valid @RequestBody ProductPageableCriteriaDto productCriteriaDto){
        return ResponseEntity.status(HttpStatus.OK).body(productService.getAllProductByCriteria(productCriteriaDto));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    @Validated(OnCreateProduct.class)
    @Operation(summary = "New product creation")
    public ResponseEntity<Void> createProduct(@Parameter(description = "New product", schema = @Schema(implementation = ProductDto.class)) @Valid @RequestBody ProductDto product) {
        productService.saveProduct(product);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping
    @Validated(OnUpdateProduct.class)
    @Operation(summary = "Product update")
    public ResponseEntity<Void> updateProduct(@Parameter(description = "Updated product", required = true) @Valid @RequestBody ProductDto product) {
        productService.updateProduct(product);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Removing a product by id")
    public ResponseEntity<Void> deleteProduct(@Parameter(description = "Id of needed product", required = true) @Min(1) @PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.ok().build();
    }
}
