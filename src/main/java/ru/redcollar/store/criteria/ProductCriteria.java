package ru.redcollar.store.criteria;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import ru.redcollar.store.dto.ProductCriteriaDto;
import ru.redcollar.store.entity.Product;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;;

public class ProductCriteria {

    public static Specification<Product> getProductSpecification(ProductCriteriaDto productCriteriaDto){
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(productCriteriaDto.getName() != null) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + productCriteriaDto.getName() + "%"));
            }
            if (productCriteriaDto.getType() != null){
                predicates.add(criteriaBuilder.equal(root.get("type"), productCriteriaDto.getType().toString()));
            }
            if(productCriteriaDto.getLowerCost() != null){
                predicates.add(criteriaBuilder.gt(root.get("cost"), productCriteriaDto.getLowerCost()));
            }
            if(productCriteriaDto.getUpperCost() != null){
                predicates.add(criteriaBuilder.lt(root.get("cost"), productCriteriaDto.getUpperCost()));
            }
            return query.where(predicates.toArray(new Predicate[0])).getRestriction();
        };
    }
}
