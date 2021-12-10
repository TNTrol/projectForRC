package ru.redcollar.store.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.redcollar.store.domain.entity.Offer;
import ru.redcollar.store.domain.entity.Product;
import ru.redcollar.store.domain.entity.StatusOffer;
import ru.redcollar.store.domain.entity.User;
import ru.redcollar.store.domain.model.OfferDto;
import ru.redcollar.store.domain.model.ProductDto;
import ru.redcollar.store.repository.OfferRepository;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OfferService {

    private final OfferRepository offerRepository;
    private final UserService userService;
    private final ProductService productService;
    private final ModelMapper modelMapper;

    public void saveOffer(OfferDto offerDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByLogin((String) authentication.getCredentials());
        List<Long> ids = offerDto.getProducts().stream()
                .map(ProductDto::getId)
                .collect(Collectors.toList());
        List<Product> products = productService.getProductsByIds(ids);
        BigDecimal cost = products.stream()
                .map(Product::getCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        Offer offer = new Offer();
        offer.setCost(cost);
        offer.setProducts(products);
        offer.setUser(user);
        offer.setDate(offerDto.getDate());
        offer.setStatus(offerDto.getStatus());
        offerRepository.save(offer);
    }

    public OfferDto getOffer(long id) {
        return modelMapper.map(offerRepository.findById(id).get(), OfferDto.class);
    }

    public List<OfferDto> getAllOffer(int page, int size) {
        if (page < 0 || size < 0) {
            return Collections.emptyList();
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByLogin((String) authentication.getCredentials());
        Pageable pageable = PageRequest.of(page, size);
        return offerRepository.findByUserId(user.getId(), pageable).stream()
                .map(offer -> modelMapper.map(offer, OfferDto.class))
                .collect(Collectors.toList());
    }

    public void sendOffer(Long offer_id) {
        Offer offer = offerRepository.findById(offer_id).get();
        offer.setStatus(StatusOffer.SENT);
        offerRepository.save(offer);
    }
}
