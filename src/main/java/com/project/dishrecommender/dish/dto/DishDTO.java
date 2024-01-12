package com.project.dishrecommender.dish.dto;

import com.project.dishrecommender.dish.entity.DishCaloricCategory;
import com.project.dishrecommender.dish.entity.DishCookingTimeCategory;
import com.project.dishrecommender.dish.entity.DishDietaryCategory;
import com.project.dishrecommender.dish.entity.DishType;
import com.project.dishrecommender.imageData.entity.ImageData;
import com.project.dishrecommender.keyword.entity.Keyword;
import com.project.dishrecommender.location.Location;
import com.project.dishrecommender.menu.entity.Menu;
import com.project.dishrecommender.preparation.entity.Preparation;

import java.time.LocalDateTime;
import java.util.List;

public record DishDTO(
        Long id,
        String name,
        String description,
        String comment,
        DishType dishType,
        DishCookingTimeCategory dishCookingTimeCategory,
        DishDietaryCategory dishDietaryCategory,
        DishCaloricCategory dishCaloricCategory,
        LocalDateTime created,
        LocalDateTime lastChange,
        List<Location> locations,
        List<Menu> menus,
        List<Keyword> keywords,
        List<Preparation> preparations,
        List<ImageData> imageData,
        double rating) {
}
