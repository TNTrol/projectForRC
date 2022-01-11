package ru.redcollar.store.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.redcollar.store.domain.entity.PackProduct;
import ru.redcollar.store.repository.ProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PackProductSrvice {

    private final ProductRepository productRepository;

    List<PackProduct> findAllPackProductByOfferIds(List<Long> ids) {
        return productRepository.findAllPackProductByOfferIds(ids);
    }
}
