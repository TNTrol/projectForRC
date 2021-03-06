package ru.redcollar.store.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.redcollar.store.criteria.OfferCriteria;
import ru.redcollar.store.dto.MailDto;
import ru.redcollar.store.dto.OfferPageableCriteriaDto;
import ru.redcollar.store.entity.Offer;
import ru.redcollar.store.entity.PackProduct;
import ru.redcollar.store.entity.Product;
import ru.redcollar.store.entity.StatusOffer;
import ru.redcollar.store.entity.User;
import ru.redcollar.store.dto.OfferDto;
import ru.redcollar.store.exceptions.ProductDontExistException;
import ru.redcollar.store.mapper.OfferMapper;
import ru.redcollar.store.mapper.ProductMapper;
import ru.redcollar.store.repository.OfferRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OfferService {

    private final OfferRepository offerRepository;
    private final UserService userService;
    private final ProductService productService;
    private final PackProductService packProductService;
    private final DeliveryService deliveryService;
    private final MailService mailService;
    private final OfferMapper offerMapper;
    private final ProductMapper productMapper;

    public OfferDto saveOffer(OfferDto offerDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByLogin((String) authentication.getCredentials());
        List<Long> ids = offerDto.getProducts().stream()
                .map(p -> p.getProduct().getId())
                .sorted()
                .collect(Collectors.toList());
        List<Product> products = productService.getProductsByIds(ids);
        List<PackProduct> productsRes = productMapper.packProductDtoToPackProduct(offerDto.getProducts());
        Offer offer = new Offer();
        for (int i = 0; i < products.size(); i++) {
            productsRes.get(i).setProduct(products.get(i));
            productsRes.get(i).setOffer(offer);
        }
        BigDecimal cost = productsRes.stream()
                .map(pacProduct1 -> pacProduct1.getProduct().getCost().multiply(new BigDecimal(pacProduct1.getCount())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        offer.setCost(cost);
        offer.setProducts((productsRes));
        offer.setUser(user);
        offer.setDate(offerDto.getDate());
        offer.setStatus(offerDto.getStatus());
        offerRepository.save(offer);
        mailService.sendMail(new MailDto(user.getEmail(), "Payment Controller Store", "Thank you for your purchase\nSum of offer: " + offer.getCost()));
        deliveryService.handDelivery(offer);
        return offerMapper.toDto(offer);
    }

    public OfferDto getOffer(long id) {
        return offerMapper.toDtoWithoutProducts(offerRepository.findById(id).get());
    }

    public List<OfferDto> getAllOffer(OfferPageableCriteriaDto offerPageableCriteriaDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Pageable pageable = PageRequest.of(offerPageableCriteriaDto.getPage(), offerPageableCriteriaDto.getSize());
        offerPageableCriteriaDto.setLogin((String) authentication.getCredentials());
        Specification<Offer> specification = OfferCriteria.getOfferSpecification(offerPageableCriteriaDto);

        List<Offer> offers = offerRepository.findAll(specification, pageable).stream().toList();
        List<Long> offerIds = offers.stream().map(Offer::getId).toList();

        List<PackProduct> products = packProductService.findAllPackProductByOfferIds(offerIds);
        Map<Long, List<PackProduct>> offerIdToPackProductMap = products.stream()
                .collect(Collectors.groupingBy(x -> x.getOffer().getId()));
        offers.forEach(offer -> offer.setProducts(offerIdToPackProductMap.get(offer.getId())));
        return offers.stream().map(offerMapper::toDto).collect(Collectors.toList());
    }

    public void sendOffer(Long offerId) {
        Optional<Offer> offerOptional = offerRepository.findById(offerId);
        if (offerOptional.isEmpty()) {
            throw new ProductDontExistException();
        }
        Offer offer = offerOptional.get();
        offer.setStatus(StatusOffer.SENT);
        offerRepository.save(offer);
    }
}
