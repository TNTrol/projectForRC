package ru.redcollar.store.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.redcollar.store.domain.entity.Product;
import ru.redcollar.store.domain.model.NewProduct;
import ru.redcollar.store.domain.model.ProductDto;
import ru.redcollar.store.domain.model.UserDto;
import ru.redcollar.store.exceptions.ProductDontExistException;
import ru.redcollar.store.exceptions.ProductExistException;
import ru.redcollar.store.repository.ProductRepository;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public void saveProduct(NewProduct newProduct) {
        if(productRepository.existsByName(newProduct.getName()))
        {
            throw new ProductExistException(newProduct.getName(), newProduct.getType().toString());
        }
        Product product = modelMapper.map(newProduct, Product.class);
        productRepository.save(product);
    }

    public void updateProduct(ProductDto productDto) {
        if (!productRepository.existsById(productDto.getId())) {
            throw new ProductDontExistException();
        }
        Product product = modelMapper.map(productDto, Product.class);
        productRepository.save(product);
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    public List<ProductDto> getAll(int page, int size) {
        int start = page * size;
        List<Product> products = productRepository.findAll();
        if (products.size() <= start) {
            return Collections.emptyList();
        }
        int end = Math.min(start + size, products.size());
        return products.subList(start, end)
                .stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .collect(Collectors.toList());

    }

    public ProductDto getUser(Long id) {
        return modelMapper.map(productRepository.getById(id), ProductDto.class);
    }
}
