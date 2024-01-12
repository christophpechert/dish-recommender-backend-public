package com.project.dishrecommender.menu;

import com.project.dishrecommender.exception.MenuAlreadyExistException;
import com.project.dishrecommender.exception.MenuNotFoundException;
import com.project.dishrecommender.menu.entity.Menu;
import com.project.dishrecommender.usergroup.UserGroupService;
import com.project.dishrecommender.usergroup.entity.UserGroup;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MenuService {
    private final UserGroupService userGroupService;
    private final MenuRepository menuRepository;

    public Menu retrieveById(Long menuId) {
        return menuRepository.findById(menuId)
                .orElseThrow(() -> new MenuNotFoundException("Id: " + menuId));
    }

    public Menu addOne(Menu menu) {
        UserGroup userGroup = userGroupService.retrieveFromUser();

        return add(userGroup, menu);
    }

    public List<Menu> addMany(List<Menu> menus) {
        UserGroup userGroup = userGroupService.retrieveFromUser();

        return menus.stream()
                .map(menu -> {
                    try {
                        return Optional.of(add(userGroup, menu));
                    } catch (MenuAlreadyExistException e) {
                        e.printStackTrace();
                        return Optional.<Menu>empty();
                    }
                })
                .flatMap(Optional::stream)
                .toList();
    }

    public Menu update(Long menuId, Menu menu) {
        Menu menuFromDb = retrieveById(menuId);

        menuFromDb.setName(menu.getName());
        menuFromDb.setDescription(menu.getDescription());

        menuFromDb.setLastChange(LocalDateTime.now());

        return save(menuFromDb);
    }

    public void deleteById(Long menuId) {
        menuRepository.deleteById(menuId);
    }

    public Menu save(Menu menu) {
        return menuRepository.save(menu);
    }

    public List<Menu> retrieveAllFromUserGroup() {
        UserGroup userGroup = userGroupService.retrieveFromUser();
        return menuRepository.findByUserGroupIdOrderByName(userGroup.getId());
    }

    private Menu add(UserGroup userGroup, Menu menu) throws MenuAlreadyExistException {
        boolean doesMenuExist = userGroup.getMenus()
                .stream()
                .anyMatch(e -> e.getName().equals(menu.getName()));

        if (doesMenuExist) {
            throw new MenuAlreadyExistException(menu.getName() + " already exist");
        }

        menu.setCreated(LocalDateTime.now());
        menu.setUserGroup(userGroup);

        return menuRepository.save(menu);
    }
}
