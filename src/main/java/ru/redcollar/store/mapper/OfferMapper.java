package ru.redcollar.store.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.redcollar.store.domain.entity.Offer;
import ru.redcollar.store.domain.model.OfferDto;

import java.util.List;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface OfferMapper {

    OfferDto toDto(Offer offer);

    Offer fromDto(OfferDto offerDto);

    List<OfferDto> toDto(List<Offer> offer);
}
