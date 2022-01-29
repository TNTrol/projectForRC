package ru.redcollar.store.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.redcollar.store.domain.entity.PackProduct;
import ru.redcollar.store.repository.PackProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PackProductService {

    private final PackProductRepository packProductRepository;

    public List<PackProduct> findAllPackProductByOfferIds(List<Long> ids) {
        return packProductRepository.findAllPackProductByOfferIds(ids);
    }
}
