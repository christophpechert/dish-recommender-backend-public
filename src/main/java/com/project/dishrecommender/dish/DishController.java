package com.project.dishrecommender.dish;

import com.project.dishrecommender.dish.dto.DishDTO;
import com.project.dishrecommender.dish.dto.DishMapper;
import com.project.dishrecommender.dish.entity.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dish-management")
@AllArgsConstructor
public class DishController {
    private final DishService dishService;
    private final DishMapper dishMapper;

    @PreAuthorize("hasAuthority('ROLE_GUEST')")
    @GetMapping("/dish/{dishId}")
    ResponseEntity<DishDTO> retrieveById(@PathVariable Long dishId) {
        Dish dish = dishService.retrieveById(dishId);
        return new ResponseEntity<>(dishMapper.dishToDishDTO(dish), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_CHEF')")
    @PostMapping("/dish")
    ResponseEntity<Dish> add(@RequestBody @Valid Dish dish) {
        return new ResponseEntity<>(dishService.addOne(dish), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ROLE_CHEF')")
    @PutMapping("/dish/{dishId}")
    ResponseEntity<DishDTO> update(@PathVariable Long dishId, @RequestBody @Valid Dish dish) {
        Dish updatedDish =  dishService.update(dishId, dish);
        return new ResponseEntity<>(dishMapper.dishToDishDTO(updatedDish), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_OWNER')")
    @DeleteMapping("/dish/{dishId}")
    ResponseEntity<Void> deleteById(@PathVariable Long dishId) {
        dishService.deleteById(dishId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasAuthority('ROLE_GUEST')")
    @GetMapping("/dish")
    ResponseEntity<List<DishDTO>> retrieveAllFromUserGroup() {
        List<DishDTO> dishes = dishService.retrieveAllFromUserGroup()
                .stream()
                .map(dishMapper::dishToDishDTO)
                .toList();
        return new ResponseEntity<>(dishes, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_GUEST')")
    @GetMapping("/dish/recommendation/{recommendationId}")
    ResponseEntity<List<DishDTO>>retrieveAllFromRecommendation(@PathVariable Long recommendationId) {
        List<DishDTO> dishes = dishService.retrieveAllFromRecommendation(recommendationId)
                .stream()
                .map(dishMapper::dishToDishDTO)
                .toList();

        return new ResponseEntity<>(dishes, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_GUEST')")
    @GetMapping("/menu/{menuId}")
    ResponseEntity<List<DishDTO>> retrieveByMenuIdAndCriteria(
            @PathVariable Long menuId,
            @RequestParam(name = "keywordids", required = false) List<Long> keywordIds,
            @RequestParam(name = "allkeywordsreq", required = false) Boolean isAllKeywordsReq,
            @RequestParam(name = "types", required = false) List<DishType> types,
            @RequestParam(name = "cookingtimecategories", required = false) List<DishCookingTimeCategory> cookingTimeCategories,
            @RequestParam(name = "dietarycategories", required = false) List<DishDietaryCategory> dietaryCategories,
            @RequestParam(name = "caloriccategories", required = false) List<DishCaloricCategory> caloricCategories) {

        List<DishDTO> dishes = dishService.retrieveByMenuIdAndCriteria(
                        menuId,
                        keywordIds,
                        isAllKeywordsReq,
                        types,
                        cookingTimeCategories,
                        dietaryCategories,
                        caloricCategories).stream()
                .map(dishMapper::dishToDishDTO)
                .toList();

        return new ResponseEntity<>(dishes, HttpStatus.OK);
    }
}
