package ru.redcollar.store.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.redcollar.store.domain.entity.*;
import ru.redcollar.store.domain.model.MailDto;
import ru.redcollar.store.domain.model.OfferDto;
import ru.redcollar.store.exceptions.ProductDontExistException;
import ru.redcollar.store.mapper.OfferMapper;
import ru.redcollar.store.mapper.ProductMapper;
import ru.redcollar.store.repository.OfferRepository;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OfferService {

    private final OfferRepository offerRepository;
    private final UserService userService;
    private final ProductService productService;
    private final PackProductSrvice packProductSrvice;
    private final MailService mailService;
    private final OfferMapper offerMapper;
    private final ProductMapper productMapper;

    public void saveOffer(OfferDto offerDto) {
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
    }

    public OfferDto getOffer(long id) {
        return offerMapper.toDto(offerRepository.findById(id).get());
    }

    public List<OfferDto> getAllOffer(int page, int size) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = (String) authentication.getCredentials();
        Pageable pageable = PageRequest.of(page, size);
        List<Offer> offers = offerRepository.findByUserLogin(login, pageable);
        List<OfferDto> offerDtos = offerMapper.toDto(offers);
        List<Long> ids = offerDtos.stream()
                .map(OfferDto::getId)
                .toList();
        List<PackProduct> products = packProductSrvice.findAllPackProductByOfferIds(ids);
        int indexProduct = 0;
        for (OfferDto offer : offerDtos) {
            for (int index = indexProduct; index < products.size() && Objects.equals(offer.getId(), products.get(index).getOffer().getId()); index++, indexProduct++) {
                PackProduct packProduct = products.get(index);
                offer.getProducts().add(productMapper.packProductToPackProductDto(packProduct));
            }
        }
        return offerDtos;
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
