package ru.redcollar.store.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.redcollar.store.entity.Offer;
import ru.redcollar.store.dto.OfferDto;

@Mapper(componentModel = "spring")
public interface OfferMapper {

    @Mapping(target = "products", ignore = true)
    OfferDto toDtoWithoutProducts(Offer offer);

    Offer fromDto(OfferDto offerDto);

    OfferDto toDto(Offer offer);

}
