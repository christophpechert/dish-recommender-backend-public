package com.project.dishrecommender.menu.dto;

public record MenuWithNumberOfDishesDTO(
        Long id,
        String name,
        String description,
        Integer numberOfDishes) {
}
