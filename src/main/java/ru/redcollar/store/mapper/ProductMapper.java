package ru.redcollar.store.mapper;

import org.mapstruct.Mapper;
import ru.redcollar.store.entity.PackProduct;
import ru.redcollar.store.entity.Product;
import ru.redcollar.store.dto.PackProductDto;
import ru.redcollar.store.dto.ProductDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDto productToProductDto(Product product);

    Product productDtoToProduct(ProductDto productDto);

    List<PackProduct> packProductDtoToPackProduct(List<PackProductDto> packProductDto);

    PackProductDto packProductToPackProductDto(PackProduct product);

}
