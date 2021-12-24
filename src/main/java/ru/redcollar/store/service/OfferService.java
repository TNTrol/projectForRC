package ru.redcollar.store.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.redcollar.store.domain.entity.*;
import ru.redcollar.store.domain.model.OfferDto;
import ru.redcollar.store.domain.model.PackProductDto;
import ru.redcollar.store.exceptions.ProductDontExistException;
import ru.redcollar.store.repository.OfferRepository;
import ru.redcollar.store.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OfferService {

    private final OfferRepository offerRepository;
    private final UserService userService;
    private final ProductService productService;
    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;
    private final SenderMailService mailService;

    public void saveOffer(OfferDto offerDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByLogin((String) authentication.getCredentials());
        List<Long> ids = offerDto.getProducts().stream()
                .map(p -> p.getProduct().getId())
                .sorted()
                .collect(Collectors.toList());
        List<Product> products = productService.getProductsByIds(ids);
        List<PackProduct> productsRes = offerDto.getProducts().stream()
                .map(p -> modelMapper.map(p, PackProduct.class))
                .collect(Collectors.toList());
        for (int i = 0; i < products.size(); i++) {
            productsRes.get(i).setProduct(products.get(i));
        }
        BigDecimal cost = productsRes.stream()
                .map(pacProduct1 -> pacProduct1.getProduct().getCost().multiply(new BigDecimal(pacProduct1.getCount())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        Offer offer = new Offer();
        offer.setCost(cost);
        offer.setProducts((productsRes));
        offer.setUser(user);
        offer.setDate(offerDto.getDate());
        offer.setStatus(offerDto.getStatus());
        offerRepository.save(offer);
        //mailService.sendMail(new Mail(user.getEmail(), "Payment Controller Store", "Thank you for your purchase\nSum of offer: " + offer.getCost()));
    }

    public OfferDto getOffer(long id) {
        return modelMapper.map(offerRepository.findById(id).get(), OfferDto.class);
    }

    public List<OfferDto> getAllOffer(int page, int size) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = userService.getIdByLogin((String) authentication.getCredentials());
        Pageable pageable = PageRequest.of(page, size);
        List<Long> ids = offerRepository.findAllIdsByUserIdWithPagination(id, pageable);
        List<PackProduct> products = productRepository.findAllPackProductByOfferIds(ids);
        List<Offer> offers = offerRepository.findAllOffer(ids);
        List<OfferDto> resOffers = new ArrayList<>();
        for(int i = 0, p = 0; i < offers.size(); i++){
            resOffers.add(modelMapper.map(offers.get(i), OfferDto.class));
            for(; p < products.size() && Objects.equals(resOffers.get(i).getId(), products.get(p).getOffer().getId()); p++) {
                resOffers.get(i).getProducts().add(modelMapper.map(products.get(p), PackProductDto.class));
            }
        }
        return resOffers;
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
