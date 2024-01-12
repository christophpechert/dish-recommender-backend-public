package com.project.dishrecommender.dish;

import com.project.dishrecommender.dish.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DishRepository extends JpaRepository<Dish, Long>, JpaSpecificationExecutor<Dish> {

    List<Dish> findByRecommendationsId(Long recommendationId);

    List<Dish> findByUserGroupIdOrderByName(Long userGroupId);
}
