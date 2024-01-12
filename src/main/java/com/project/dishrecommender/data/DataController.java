package com.project.dishrecommender.data;

import com.project.dishrecommender.dish.DishService;
import com.project.dishrecommender.dish.entity.Dish;
import com.project.dishrecommender.keyword.KeywordService;
import com.project.dishrecommender.keyword.entity.Keyword;
import com.project.dishrecommender.menu.MenuService;
import com.project.dishrecommender.menu.entity.Menu;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/data-management")
@AllArgsConstructor
public class DataController {
    private final DishService dishService;
    private final KeywordService keywordService;
    private final MenuService menuService;

    @PreAuthorize("hasAuthority('ROLE_WAITER')")
    @GetMapping("/dishes")
    List<Dish> retrieveAllDishesFromUser() {
        return dishService.retrieveAllFromUserGroup();
    }

    @PreAuthorize("hasAuthority('ROLE_CHEF')")
    @PostMapping("/dishes")
    List<Dish> addDishes(@RequestBody List<Dish> dishes) {
        return dishService.addMany(dishes);
    }

    @PreAuthorize("hasAuthority('ROLE_WAITER')")
    @GetMapping("/keywords")
    List<Keyword> retrieveAllKeywordsFromUser() {
        return keywordService.retrieveAllFromUserGroup();
    }

    @PreAuthorize("hasAuthority('ROLE_CHEF')")
    @PostMapping("/keywords")
    List<Keyword> addKeywords(@RequestBody List<Keyword> keywords) {
        return keywordService.addMany(keywords);
    }

    @PreAuthorize("hasAuthority('ROLE_WAITER')")
    @GetMapping("menus")
    List<Menu> retrieveAllMenusFromUser() {
        return menuService.retrieveAllFromUserGroup();
    }

    @PreAuthorize("hasAuthority('ROLE_CHEF')")
    @PostMapping("/menus")
    List<Menu> addMenus(@RequestBody List<Menu> menus) {
            return menuService.addMany(menus);
    }
}
