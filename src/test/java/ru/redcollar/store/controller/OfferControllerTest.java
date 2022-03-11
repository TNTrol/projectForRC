package ru.redcollar.store.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import java.time.Instant;
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
import ru.redcollar.store.dto.OfferDto;
import ru.redcollar.store.dto.PackProductDto;
import ru.redcollar.store.dto.ProductDto;
import ru.redcollar.store.entity.PackProduct;
import ru.redcollar.store.entity.StatusOffer;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles(profiles = "testcontainers")
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OfferControllerTest {

    private final MockMvc mockMvc;

    @Test
    @WithMockUser(roles = "ADMIN")
    @Sql("/db_offer.sql")
    @Transactional
    public void successfullyCreationOffer() throws Exception {
        OfferDto offerDto = new OfferDto();
        Instant date = Instant.now();
        offerDto.setDate(date);
        offerDto.setStatus(StatusOffer.PAID);
        for(long i = 1; i < 4; i++){
            ProductDto productDto = new ProductDto();
            productDto.setId(i);
            PackProductDto packProductDto = new PackProductDto();
            packProductDto.setProduct(productDto);
            packProductDto.setCount((int) (i * 2));
            offerDto.getProducts().add(packProductDto);
        }
        mockMvc.perform(post("/offer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(offerDto)))
                .andExpect(status().isOk());
    }
}
