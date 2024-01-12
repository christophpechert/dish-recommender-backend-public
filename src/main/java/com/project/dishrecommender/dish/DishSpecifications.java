package com.project.dishrecommender.dish;

import com.project.dishrecommender.dish.entity.Dish;
import com.project.dishrecommender.keyword.entity.Keyword;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class DishSpecifications {

    public static Specification<Dish> hasMenuId(Long menuId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("menus").get("id"), menuId);
    }

    public static Specification<Dish> hasKeywordIds(List<Long> keywordIds) {
        return (root, query, criteriaBuilder) -> {
            if (keywordIds == null || keywordIds.isEmpty()) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }

            Join<Dish, Keyword> keywordJoin = root.join("keywords");

            query.distinct(true);

            return keywordJoin.get("id").in(keywordIds);
        };
    }

    public static <T> Specification<Dish> hasCategory(String categoryProperty, List<T> categories) {
        return (root, query, criteriaBuilder) -> {
            if (categories == null || categories.isEmpty()) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }

            return root.get(categoryProperty).in(categories);
        };
    }

    public static Specification<Dish> orderByName() {
        return (root, query, criteriaBuilder) -> {
            query.orderBy(criteriaBuilder.asc(root.get("name")));
            return query.getRestriction();
        };
    }
}
