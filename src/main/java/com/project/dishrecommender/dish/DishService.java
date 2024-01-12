package com.project.dishrecommender.dish;

import com.project.dishrecommender.dish.entity.*;
import com.project.dishrecommender.exception.DishNotFoundException;
import com.project.dishrecommender.keyword.entity.Keyword;
import com.project.dishrecommender.recommendation.RecommendationService;
import com.project.dishrecommender.usergroup.UserGroupService;
import com.project.dishrecommender.usergroup.entity.UserGroup;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class DishService {
    private final UserGroupService userGroupService;
    private final DishRepository dishRepository;
    private final RecommendationService recommendationService;

    public Dish retrieveById(Long dishId) {
        return dishRepository.findById(dishId)
                .orElseThrow(() -> new DishNotFoundException("Id: " + dishId));
    }

    public Dish update(Long dishId, Dish dish) {
        Dish dishFromDb = retrieveById(dishId);

        dishFromDb.setName(dish.getName());
        dishFromDb.setDescription(dish.getDescription());
        dishFromDb.setComment(dish.getComment());
        dishFromDb.setDishType(dish.getDishType());
        dishFromDb.setDishCookingTimeCategory(dish.getDishCookingTimeCategory());
        dishFromDb.setDishDietaryCategory(dish.getDishDietaryCategory());
        dishFromDb.setDishCaloricCategory(dish.getDishCaloricCategory());
        dishFromDb.setLastChange(LocalDateTime.now());
        dishFromDb.setLocations(dish.getLocations());
        dishFromDb.setKeywords(dish.getKeywords());
        dishFromDb.setMenus(dish.getMenus());

        return save(dishFromDb);
    }

    @Transactional
    public void deleteById(Long dishId) {
        Dish dish = retrieveById(dishId);

        dish.getRecommendations()
                .forEach(recommendation -> recommendationService.removeDishById(recommendation.getId(), dishId));

        dishRepository.deleteById(dishId);
    }

    public Dish addOne(Dish dish) {
        UserGroup userGroup = userGroupService.retrieveFromUser();
        return add(userGroup, dish);
    }

    public List<Dish> addMany(List<Dish> dishes) {
        UserGroup userGroup = userGroupService.retrieveFromUser();
        return dishes.stream()
                .map(e -> add(userGroup, e))
                .toList();
    }

    public List<Dish> retrieveAllFromUserGroup() {
        UserGroup userGroup = userGroupService.retrieveFromUser();
        return dishRepository.findByUserGroupIdOrderByName(userGroup.getId());
    }

    public List<Dish> retrieveAllFromRecommendation(Long recommendationId) {
        return dishRepository.findByRecommendationsId(recommendationId);
    }

    public List<Dish> retrieveByMenuIdAndCriteria(
            Long menuId,
            List<Long> keywordIds,
            Boolean isAllKeywordsReq,
            List<DishType> types,
            List<DishCookingTimeCategory> cookingTimeCategories,
            List<DishDietaryCategory> dietaryCategories,
            List<DishCaloricCategory> caloricCategories) {

        Specification<Dish> spec = createSpecification(menuId, keywordIds, types, cookingTimeCategories, dietaryCategories ,caloricCategories);

        List<Dish> dishes = dishRepository.findAll(spec);

        dishes.forEach(e -> System.out.println(e.getName()));

        if (isAllKeywordsReq != null && isAllKeywordsReq) {
            return dishes.stream()
                    .filter(dish ->
                            keywordIds.stream()
                                    .allMatch(keywordId -> dish.getKeywords()
                                            .stream()
                                            .anyMatch(keyword -> keyword.getId().equals(keywordId))))
                    .toList();
        }

        return dishes;
    }

    private Dish add(UserGroup userGroup, Dish dish) {
        dish.setCreated(LocalDateTime.now());
        dish.setUserGroup(userGroup);

        return save(dish);
    }

    private Dish save(Dish dish) {
        return dishRepository.save(dish);
    }

    private List<Long> retrieveAllKeywordIds(UserGroup userGroup) {
        return userGroup.getKeywords()
                .stream()
                .map(Keyword::getId)
                .toList();
    }

    private Specification<Dish> createSpecification(
            Long menuId,
            List<Long> keywordIds,
            List<DishType> types,
            List<DishCookingTimeCategory> cookingTimeCategories,
            List<DishDietaryCategory> dietaryCategories,
            List<DishCaloricCategory> caloricCategories) {

        Specification<Dish> spec = Specification.where(DishSpecifications.hasMenuId(menuId));

        if (keywordIds != null) {
            spec = spec.and(DishSpecifications.hasKeywordIds(keywordIds));
        }

        if (types != null) {
            spec = spec.and(DishSpecifications.hasCategory("dishType", types));
        }

        if (types != null) {
            spec = spec.and(DishSpecifications.hasCategory("dishCookingTimeCategory", cookingTimeCategories));
        }

        if (types != null) {
            spec = spec.and(DishSpecifications.hasCategory("dishDietaryCategory", dietaryCategories));
        }

        if (types != null) {
            spec = spec.and(DishSpecifications.hasCategory("dishCaloricCategory", caloricCategories));
        }

        spec = spec.and(DishSpecifications.orderByName());

        return spec;
    }
}
