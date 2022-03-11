package ru.redcollar.store.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import ru.redcollar.store.dto.ProductDto;
import ru.redcollar.store.entity.TypeProduct;
import ru.redcollar.store.exceptions.ProductDontExistException;
import ru.redcollar.store.exceptions.ProductExistException;
import ru.redcollar.store.mapper.ProductMapper;
import ru.redcollar.store.repository.ProductRepository;
import ru.redcollar.store.service.ProductService;

import java.math.BigDecimal;


@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    private ProductService productService;


    @Before
    public void initService() {
        productService = new ProductService(productRepository, productMapper);
    }

    @Test(expected = ProductExistException.class)
    public void createDoubleProductTest() {
        Mockito.when(productRepository.existsByName("arduino")).thenReturn(true);
        ProductDto product = new ProductDto(0L, "simple", "arduino", TypeProduct.CONTROLLER, new BigDecimal("300.56"));
        productService.saveProduct(product);
    }

    @Test
    public void updateProductTest() {
        Mockito.when(productRepository.existsById(1L)).thenReturn(true);
        ProductDto product = new ProductDto(1L, "simple", "arduino", TypeProduct.CONTROLLER, new BigDecimal("300.56"));
        productService.updateProduct(product);
    }

    @Test(expected = ProductDontExistException.class)
    public void updateDontExistsProductTest() {
        Mockito.when(productRepository.existsById(1L)).thenReturn(false);
        ProductDto product = new ProductDto(1L, "simple", "arduino", TypeProduct.CONTROLLER, new BigDecimal("300.56"));
        productService.updateProduct(product);
    }
}
