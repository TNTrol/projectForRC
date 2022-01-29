package ru.redcollar.store.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.redcollar.store.domain.entity.Offer;
import ru.redcollar.store.domain.model.OfferDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OfferMapper {

    @Mapping(target = "products", ignore = true)
    OfferDto toDtoWithoutProducts(Offer offer);

    Offer fromDto(OfferDto offerDto);

    OfferDto toDto(Offer offer);

}
