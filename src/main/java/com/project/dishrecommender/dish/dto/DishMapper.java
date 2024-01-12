package com.project.dishrecommender.dish.dto;

import com.project.dishrecommender.dish.entity.Dish;
import com.project.dishrecommender.preparation.entity.Preparation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DishMapper {
    @Mapping(source = "preparations", target = "rating", qualifiedByName = "preparationsToRating")
    DishDTO dishToDishDTO(Dish dish);

    @Named("preparationsToRating")
    static double preparationsToRating(List<Preparation> preparations) {
        return preparations.stream()
                .mapToDouble(Preparation::getRating)
                .average()
                .orElse(0.0);
    }
}
