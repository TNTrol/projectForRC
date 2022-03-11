package ru.redcollar.store.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.redcollar.store.dto.ProductDto;
import ru.redcollar.store.entity.TypeProduct;
import ru.redcollar.store.exceptions.ProductDontExistException;
import ru.redcollar.store.exceptions.ProductExistException;
import ru.redcollar.store.mapper.ProductMapper;
import ru.redcollar.store.repository.ProductRepository;

import java.math.BigDecimal;

@SpringBootTest
@AutoConfigureMockMvc
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductServiceTest {

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private ProductMapper productMapper;

    private final ProductService productService;

    @Test
    public void createDoubleProductTest() {
        ProductDto product = new ProductDto(0L, "simple", "arduino", TypeProduct.CONTROLLER, new BigDecimal("300.56"));
        Mockito.when(productRepository.existsByName("arduino")).thenReturn(true);
        Assertions.assertThrows(ProductExistException.class, () -> productService.saveProduct(product));
    }

    @Test
    public void updateProductTest() {
        Mockito.when(productRepository.existsById(1L)).thenReturn(true);
        ProductDto product = new ProductDto(1L, "simple", "arduino", TypeProduct.CONTROLLER, new BigDecimal("300.56"));
        productService.updateProduct(product);
    }

    @Test
    public void updateDontExistsProductTest() {
        ProductDto product = new ProductDto(1L, "simple", "arduino", TypeProduct.CONTROLLER, new BigDecimal("300.56"));
        Mockito.when(productRepository.existsById(1L)).thenReturn(false);
        Assertions.assertThrows(ProductDontExistException.class, () -> productService.updateProduct(product));
    }
}
