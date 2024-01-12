package com.project.dishrecommender.recommendation;

import com.project.dishrecommender.dish.DishService;
import com.project.dishrecommender.exception.NoDishesForRecommendationException;
import com.project.dishrecommender.exception.RecommendationNoMemberException;
import com.project.dishrecommender.exception.RecommendationNotFoundException;
import com.project.dishrecommender.menu.MenuService;
import com.project.dishrecommender.recommendation.entity.Recommendation;
import com.project.dishrecommender.usergroup.UserGroupService;
import com.project.dishrecommender.usergroup.entity.UserGroup;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
public class RecommendationService {
    private final RecommendationRepository recommendationRepository;
    private final UserGroupService userGroupService;

    public Recommendation retrieveById(Long recommendationId) {
        return recommendationRepository.findById(recommendationId)
                .orElseThrow(() -> new RecommendationNotFoundException(recommendationId));
    }

    @Transactional
    public Recommendation add(Recommendation recommendation) {
        UserGroup userGroup = userGroupService.retrieveFromUser();

        if (recommendation.getDishes().isEmpty()) {
            throw new NoDishesForRecommendationException();
        }

        recommendation.setCreated(LocalDateTime.now());
        recommendation.setUserGroup(userGroup);

        return recommendationRepository.save(recommendation);
    }

    public List<Recommendation> retrieveByMenuId(Long menuId) {
        return recommendationRepository.findByMenuIdOrderByName(menuId);
    }

    public Recommendation update(Long recommendationId, Recommendation recommendation) {
        Recommendation recommendationFromDb = retrieveById(recommendationId);
        recommendationFromDb.setName(recommendation.getName());
        recommendationFromDb.setLastChange(LocalDateTime.now());

        return recommendationRepository.save(recommendationFromDb);
    }

    public Recommendation removeDishById(Long recommendationId, Long dishId) {
        UserGroup userGroup = userGroupService.retrieveFromUser();
        Recommendation recommendation = retrieveById(recommendationId);

        if (!Objects.equals(userGroup.getId(), recommendation.getUserGroup().getId())) {
            throw new RecommendationNoMemberException(recommendationId);
        }

        recommendation.getDishes().removeIf(e -> Objects.equals(e.getId(), dishId));

        return recommendationRepository.save(recommendation);
    }

    public void deleteRecommendation(Long recommendationId) {
        UserGroup userGroup = userGroupService.retrieveFromUser();
        Recommendation recommendation = retrieveById(recommendationId);

        if (!Objects.equals(userGroup.getId(), recommendation.getUserGroup().getId())) {
            throw new RecommendationNoMemberException(recommendationId);
        }

        recommendationRepository.deleteById(recommendationId);
    }
}
