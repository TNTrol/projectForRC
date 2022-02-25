import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.redcollar.store.entity.*;
import ru.redcollar.store.mapper.OfferMapper;
import ru.redcollar.store.mapper.ProductMapper;
import ru.redcollar.store.repository.OfferRepository;
import ru.redcollar.store.service.*;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class OfferTest {

    @Mock
    OfferRepository offerRepository;

    @Mock
    ProductService productService;

    @Mock
    PackProductService packProductService;

    @Mock
    DeliveryService deliveryService;

    @Mock
    MailService mailService;

    @Mock
    UserService userService;

    @Mock
    Authentication authentication;

    @Mock
    SecurityContext securityContext;

    ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);

    OfferMapper offerMapper = Mappers.getMapper(OfferMapper.class);

    OfferService offerService;

    @Before
    public void before() {
        offerService = new OfferService(offerRepository, userService, productService, packProductService, deliveryService, mailService, offerMapper, productMapper);
    }

    @Test
    public void saveOffer() {
        User user = new User();//1L, "test_name", "password", "test_login", "test_email", "test_address", null
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
            Offer offer2 = o.getArgument(0);
            assert (offer2.getProducts().size() == packProducts.size());
            for (int i = 0; i < packProducts.size(); i++) {
                assert offer2.getProducts().get(i).getProduct() == packProducts.get(i).getProduct();
            }
            assert (offer2.getUser() == offer.getUser());
            return null;
        });
        Mockito.when(authentication.getCredentials()).thenReturn("test_login");
        Mockito.when(userService.getUserByLogin("test_login")).thenReturn(user);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        offerService.saveOffer(offerMapper.toDto(offer));
        assert (true);
    }
}
