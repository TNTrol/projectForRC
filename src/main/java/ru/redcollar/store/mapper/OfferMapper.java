package ru.redcollar.store.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import ru.redcollar.store.domain.entity.Offer;
import ru.redcollar.store.domain.model.OfferDto;

import java.util.List;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface OfferMapper {

    //@Mapping(target = "user", ignore = true)
    @Mapping(target = "products", ignore = true)
    OfferDto offerToOfferDto(Offer offer);

    Offer offerDtoToOffer(OfferDto offerDto);

    List<OfferDto> offerListToOfferDtoList(List<Offer> offer);
}
