package com.project.dishrecommender.menu.dto;

import com.project.dishrecommender.menu.entity.Menu;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MenuMapper {
        MenuWithDishesDTO menuToMenuWithDishesDTO(Menu menu);

    default MenuWithNumberOfDishesDTO menuToMenuWithNumberOfDishesDTO(Menu menu) {
        int numberOfDishes = menu.getDishes().size();
        return new MenuWithNumberOfDishesDTO(menu.getId(), menu.getName(), menu.getDescription(), numberOfDishes);
    }

}
