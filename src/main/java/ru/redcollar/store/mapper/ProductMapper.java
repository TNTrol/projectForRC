package ru.redcollar.store.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.redcollar.store.domain.entity.PackProduct;
import ru.redcollar.store.domain.entity.Product;
import ru.redcollar.store.domain.model.PackProductDto;
import ru.redcollar.store.domain.model.ProductDto;

import java.util.List;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ProductMapper {

    ProductDto productToProductDto(Product product);

    Product productDtoToProduct(ProductDto productDto);

    List<PackProduct> packProductDtoToPackProduct(List<PackProductDto> packProductDto);

    PackProductDto packProductToPackProductDto(PackProduct product);

}
