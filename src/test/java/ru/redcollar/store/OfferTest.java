package ru.redcollar.store;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.redcollar.store.dto.OfferDto;
import ru.redcollar.store.entity.*;
import ru.redcollar.store.mapper.OfferMapper;
import ru.redcollar.store.mapper.ProductMapper;
import ru.redcollar.store.repository.OfferRepository;
import ru.redcollar.store.service.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class OfferTest {

    @Mock
    private OfferRepository offerRepository;

    @Mock
    private ProductService productService;

    @Mock
    private PackProductService packProductService;

    @Mock
    private DeliveryService deliveryService;

    @Mock
    private MailService mailService;

    @Mock
    private UserService userService;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    private ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);

    private OfferMapper offerMapper = Mappers.getMapper(OfferMapper.class);

    private OfferService offerService;

    @Before
    public void before() {
        offerService = new OfferService(offerRepository, userService, productService, packProductService, deliveryService, mailService, offerMapper, productMapper);
    }

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

        assert (offerDtoTarget.getId().equals(10L));
        assert (offerDtoTarget.getCost().equals(new BigDecimal(454)));
        assert (offerDtoTarget.getProducts().size() == offerDtoSrc.getProducts().size());
        for (int i = 0; i < offerDtoSrc.getProducts().size(); i++) {
            assert Objects.equals(offerDtoSrc.getProducts().get(i).getProduct(), offerDtoTarget.getProducts().get(i).getProduct());
        }
    }
}
