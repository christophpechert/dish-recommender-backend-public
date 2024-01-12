package com.project.dishrecommender.menu.dto;

import com.project.dishrecommender.dish.dto.DishDTO;

import java.util.List;

public record MenuWithDishesDTO(
        Long id,
        String name,
        String description,
        List<DishDTO> dishes) {
}
