package ru.redcollar.store.criteria;

import org.springframework.data.jpa.domain.Specification;
import ru.redcollar.store.dto.OfferPageableCriteriaDto;
import ru.redcollar.store.entity.Offer;
import ru.redcollar.store.entity.PackProduct;
import ru.redcollar.store.entity.Product;
import ru.redcollar.store.entity.User;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Predicate;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class OfferCriteria {

    public static Specification<Offer> getOfferSpecification(OfferPageableCriteriaDto offerCriteriaDto) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            Join<Offer, User> userJoin = root.join("user");
            predicates.add(criteriaBuilder.equal(userJoin.get("login"), offerCriteriaDto.getLogin()));
            if (offerCriteriaDto.getProductName() != null) {
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
            if (offerCriteriaDto.getWith() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("date"), offerCriteriaDto.getWith()));
            }
            if (offerCriteriaDto.getTo() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("date"), offerCriteriaDto.getTo()));
            }
            return query.where(predicates.toArray(new Predicate[0])).getRestriction();
        };
    }
}
