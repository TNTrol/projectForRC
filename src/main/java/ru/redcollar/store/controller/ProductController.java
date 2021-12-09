package ru.redcollar.store.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.redcollar.store.domain.model.NewProduct;
import ru.redcollar.store.domain.model.ProductDto;
import ru.redcollar.store.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public void createProduct(@RequestBody NewProduct product) {
        productService.saveProduct(product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.delete(id);
    }

    @PutMapping
    public void updateProduct(@RequestBody ProductDto product) {
        productService.updateProduct(product);
    }

    @GetMapping("/list/{page}/{size}")
    public List<ProductDto> getAllProduct(@PathVariable int page, @PathVariable int size) {
        return productService.getAll(page, size);
    }
}
