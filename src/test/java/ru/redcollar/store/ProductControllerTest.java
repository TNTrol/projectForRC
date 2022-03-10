package ru.redcollar.store;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.redcollar.store.dto.ProductDto;
import ru.redcollar.store.entity.TypeProduct;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles(profiles = "testcontainers")
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductControllerTest {

    private final MockMvc mockMvc;

    @Test
    @WithMockUser(roles = "ADMIN")
    @Sql("/db.sql")
    @Transactional
    public void successfullyCreationProduct() throws Exception {
        ProductDto productDto = new ProductDto();
        productDto.setCost(new BigDecimal(100));
        productDto.setDescription("test");
        productDto.setType(TypeProduct.CONTROLLER);
        productDto.setName("test_name");
        mockMvc.perform(post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(productDto)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @Sql("/db.sql")
    @Transactional
    public void unsuccessfullyUpdateProduct() throws Exception {
        ProductDto productDto = new ProductDto();
        productDto.setCost(new BigDecimal(100));
        productDto.setDescription("test");
        productDto.setType(TypeProduct.CONTROLLER);
        productDto.setName("test_name");
        productDto.setId(1L);
        mockMvc.perform(put("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(productDto)))
                .andExpect(status().isNotFound());
    }
}
