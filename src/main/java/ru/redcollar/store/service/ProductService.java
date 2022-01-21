package ru.redcollar.store.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.redcollar.store.domain.entity.Product;
import ru.redcollar.store.domain.model.ProductDto;
import ru.redcollar.store.exceptions.ProductDontExistException;
import ru.redcollar.store.exceptions.ProductExistException;
import ru.redcollar.store.mapper.ProductMapper;
import ru.redcollar.store.repository.ProductRepository;

import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public void saveProduct(ProductDto productDto) {
        if (productRepository.existsByName(productDto.getName())) {
            log.error("Product {} exist!", productDto.getName());
            throw new ProductExistException(productDto.getName(), productDto.getType().toString());
        }
        Product product = productMapper.productDtoToProduct(productDto);
        productRepository.save(product);
    }

    public void updateProduct(ProductDto productDto) {
        if (!productRepository.existsById(productDto.getId())) {
            log.error("Product {} don't exist!", productDto.getName());
            throw new ProductDontExistException();
        }
        Product product = productMapper.productDtoToProduct(productDto);
        productRepository.save(product);
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    public List<ProductDto> getAll(int page, int size) {
        if (page < 0 || size < 0) {
            return Collections.emptyList();
        }
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findAll(pageable)
                .stream()
                .map(productMapper::productToProductDto)
                .collect(Collectors.toList());
    }

    public ProductDto getUser(Long id) {
        return productMapper.productToProductDto(productRepository.getById(id));
    }

    public List<Product> getProductsByIds(List<Long> ids) {
        return productRepository.findByIds(ids);
    }
}
