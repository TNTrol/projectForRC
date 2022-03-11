package ru.redcollar.store.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.redcollar.store.dto.OfferDto;
import ru.redcollar.store.entity.*;
import ru.redcollar.store.mapper.OfferMapper;
import ru.redcollar.store.mapper.ProductMapper;
import ru.redcollar.store.repository.OfferRepository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@AutoConfigureMockMvc
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OfferServiceTest {

    @MockBean
    private OfferRepository offerRepository;

    @MockBean
    private ProductService productService;

    @MockBean
    private PackProductService packProductService;

    @MockBean
    private DeliveryService deliveryService;

    @MockBean
    private MailService mailService;

    @MockBean
    private UserService userService;

    @MockBean
    private Authentication authentication;

    @MockBean
    private SecurityContext securityContext;

    private ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);

    private OfferMapper offerMapper = Mappers.getMapper(OfferMapper.class);

    private final OfferService offerService;

    @Test
    public void saveOffer() {
        User user = new User();
        Offer offer = new Offer(null, user, new BigDecimal(100), Instant.now(), StatusOffer.PAID, new ArrayList<>());
        List<PackProduct> packProducts = Arrays.asList(
                new PackProduct(1L, new Product(1L, "test1", "test1_d", TypeProduct.CONTROLLER, new BigDecimal(100)), offer, 2),
                new PackProduct(2L, new Product(3L, "test2", "test2_d", TypeProduct.CONTROLLER, new BigDecimal(150)), offer, 1),
                new PackProduct(3L, new Product(5L, "test3", "test3_d", TypeProduct.SENSOR, new BigDecimal(52)), offer, 2)
        );
        List<Product> products = packProducts.stream().map(PackProduct::getProduct).toList();
        offer.setProducts(packProducts);

        Mockito.when(productService.getProductsByIds(any())).thenReturn(products);
        Mockito.when(offerRepository.save(any())).then(o -> {
            ((Offer) o.getArgument(0)).setId(10L);
            return null;
        });
        Mockito.when(authentication.getCredentials()).thenReturn("test_login");
        Mockito.when(userService.getUserByLogin("test_login")).thenReturn(user);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        OfferDto offerDtoTarget = offerService.saveOffer(offerMapper.toDto(offer));
        OfferDto offerDtoSrc = offerMapper.toDto(offer);

        Assertions.assertEquals(10L, (long) offerDtoTarget.getId());
        Assertions.assertEquals(offerDtoTarget.getCost(), new BigDecimal(454));
        assert (offerDtoTarget.getProducts().size() == offerDtoSrc.getProducts().size());
        for (int i = 0; i < offerDtoSrc.getProducts().size(); i++) {
            Assertions.assertEquals(offerDtoSrc.getProducts().get(i).getProduct(), offerDtoTarget.getProducts().get(i).getProduct());
        }
    }
}
