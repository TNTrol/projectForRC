package ru.redcollar.store.criteria;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import ru.redcollar.store.dto.OfferPageableCriteriaDto;
import ru.redcollar.store.entity.Offer;
import ru.redcollar.store.entity.PackProduct;
import ru.redcollar.store.entity.Product;
import ru.redcollar.store.entity.User;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class OfferCriteria {

    public static Specification<Offer> getOfferSpecification(OfferPageableCriteriaDto offerCriteriaDto) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            Join<Offer, User> userJoin = root.join("user");
            predicates.add(criteriaBuilder.equal(userJoin.get("login"), offerCriteriaDto.getLogin()));
            if (StringUtils.isNotBlank(offerCriteriaDto.getProductName())) {
                ListJoin<Offer, PackProduct> packProductJoin = root.joinList("products");
                Join<PackProduct, Product> productJoin = packProductJoin.join("product");
                predicates.add(criteriaBuilder.like(productJoin.get("name"), "%" + offerCriteriaDto.getProductName() + "%"));
            }
            if (offerCriteriaDto.getLowerCost() != null) {
                predicates.add(criteriaBuilder.gt(root.get("cost"), offerCriteriaDto.getLowerCost()));
            }
            if (offerCriteriaDto.getUpperCost() != null) {
                predicates.add(criteriaBuilder.lt(root.get("cost"), offerCriteriaDto.getUpperCost()));
            }
            if (offerCriteriaDto.getWithDate() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("date"), offerCriteriaDto.getWithDate()));
            }
            if (offerCriteriaDto.getToDate() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("date"), offerCriteriaDto.getToDate()));
            }
            return query.where(predicates.toArray(new Predicate[0])).getRestriction();
        };
    }
}
