package ru.redcollar.store.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.redcollar.store.dto.*;
import ru.redcollar.store.entity.StatusOffer;
import ru.redcollar.store.service.DeliveryService;
import ru.redcollar.store.service.MailService;

import java.time.Instant;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles(profiles = "testcontainers")
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Sql("/db_offer.sql")
public class OfferControllerTest {

    private final MockMvc mockMvc;

    @MockBean
    private final MailService mailService;

    @MockBean
    private final DeliveryService deliveryService;

    @Test
    @WithMockUser(roles = "ADMIN", password = "admin")
    @Transactional
    public void successfullyCreationOffer() throws Exception {
        OfferDto offerDto = new OfferDto();
        Instant date = Instant.now();
        offerDto.setDate(date);
        offerDto.setStatus(StatusOffer.PAID);
        ObjectMapper objectMapper = new ObjectMapper();
        for (long i = 1; i < 4; i++) {
            ProductDto productDto = new ProductDto();
            productDto.setId(i);
            PackProductDto packProductDto = new PackProductDto();
            packProductDto.setProduct(productDto);
            packProductDto.setCount(1);
            offerDto.getProducts().add(packProductDto);
        }
        mockMvc.perform(post("/offer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(offerDto)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.cost").value("500.1"));
    }

    @Test
    @WithMockUser(roles = "ADMIN", password = "admin")
    @Transactional
    public void successfullyCreationOfferWithDifferentCount() throws Exception {
        OfferDto offerDto = new OfferDto();
        Instant date = Instant.now();
        offerDto.setDate(date);
        offerDto.setStatus(StatusOffer.PAID);
        ObjectMapper objectMapper = new ObjectMapper();
        for (long i = 1; i < 4; i++) {
            ProductDto productDto = new ProductDto();
            productDto.setId(i);
            PackProductDto packProductDto = new PackProductDto();
            packProductDto.setProduct(productDto);
            packProductDto.setCount((int) (i * 2));
            offerDto.getProducts().add(packProductDto);
        }
        mockMvc.perform(post("/offer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(offerDto)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.cost").value("2300.2"));
    }

    @Test
    @WithMockUser(roles = "ADMIN", password = "admin")
    @Transactional
    public void invalidCreationOffer() throws Exception {
        OfferDto offerDto = new OfferDto();
        Instant date = Instant.now();
        offerDto.setDate(date);
        offerDto.setStatus(StatusOffer.PAID);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/offer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(offerDto)))
                .andExpect(status().isBadRequest());
    }

}
