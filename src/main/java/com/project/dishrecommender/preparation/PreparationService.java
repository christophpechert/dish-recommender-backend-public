package com.project.dishrecommender.preparation;

import com.project.dishrecommender.dish.DishService;
import com.project.dishrecommender.dish.entity.Dish;
import com.project.dishrecommender.exception.DishNoMemberOfUserGroupException;
import com.project.dishrecommender.preparation.entity.Preparation;
import com.project.dishrecommender.user.UserInfoService;
import com.project.dishrecommender.user.entity.UserInfo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class PreparationService {
    private final PreparationRepository preparationRepository;
    private final UserInfoService userInfoService;
    private final DishService dishService;

    public Preparation add(Long dishId, Preparation preparation) {
        UserInfo user = userInfoService.retrieveByAuth();
        Dish dish = dishService.retrieveById(dishId);

        if (!Objects.equals(dish.getUserGroup().getId(), user.getUserGroup().getId())) {
            throw new DishNoMemberOfUserGroupException(dishId);
        }

        preparation.setDish(dish);
        preparation.setUserInfo(user);
        preparation.setCreated(LocalDateTime.now());

        return preparationRepository.save(preparation);
    }

    public List<Preparation> retrieveAllFromDish(Long dishId) {
        return preparationRepository.findByDishIdOrderByCookedDesc(dishId);
    }
}
