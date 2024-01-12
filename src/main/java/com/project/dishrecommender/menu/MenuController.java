package com.project.dishrecommender.menu;

import com.project.dishrecommender.menu.dto.MenuMapper;
import com.project.dishrecommender.menu.dto.MenuWithDishesDTO;
import com.project.dishrecommender.menu.dto.MenuWithNumberOfDishesDTO;
import com.project.dishrecommender.menu.entity.Menu;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu-management")
@AllArgsConstructor
public class MenuController {
    private final MenuService menuService;
    private final MenuMapper menuMapper;

    @PreAuthorize("hasAuthority('ROLE_GUEST')")
    @GetMapping("/menu")
    public ResponseEntity<List<MenuWithNumberOfDishesDTO>> retrieveAllWithNumberOfDishes() {
        List<MenuWithNumberOfDishesDTO> menus = menuService.retrieveAllFromUserGroup()
                .stream()
                .map(menuMapper::menuToMenuWithNumberOfDishesDTO)
                .toList();

        return new ResponseEntity<>(menus, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_CHEF')")
    @PostMapping("/menu")
    public ResponseEntity<Menu> add(@RequestBody @Valid Menu menu) {
        return new ResponseEntity<>(menuService.addOne(menu), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ROLE_CHEF')")
    @PutMapping("/menu/{menuId}")
    public ResponseEntity<Menu> update(@PathVariable Long menuId, @RequestBody @Valid Menu menu) {
        return new ResponseEntity<>(menuService.update(menuId, menu), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_OWNER')")
    @DeleteMapping("/menu/{menuId}")
    public ResponseEntity<Void> deleteById(@PathVariable Long menuId) {
        menuService.deleteById(menuId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasAuthority('ROLE_GUEST')")
    @GetMapping("/menu/{menuId}")
    public ResponseEntity<MenuWithDishesDTO> retrieveWithDishesById(@PathVariable Long menuId) {
        return new ResponseEntity<>(menuMapper.menuToMenuWithDishesDTO(menuService.retrieveById(menuId)), HttpStatus.OK);
    }
}
