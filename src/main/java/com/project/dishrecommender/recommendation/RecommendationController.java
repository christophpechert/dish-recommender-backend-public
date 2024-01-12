package com.project.dishrecommender.recommendation;

import com.project.dishrecommender.recommendation.entity.Recommendation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recommendation-management")
@AllArgsConstructor
public class RecommendationController {
    private final RecommendationService recommendationService;

    @PreAuthorize("hasAuthority('ROLE_WAITER')")
    @PostMapping("/recommendation")
    ResponseEntity<Recommendation> add(@RequestBody Recommendation recommendation) {
        return new ResponseEntity<>(recommendationService.add(recommendation), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ROLE_GUEST')")
    @GetMapping("recommendation/menu/{menuId}")
    List<Recommendation> retrieveByMenuId(@PathVariable Long menuId) {
        return recommendationService.retrieveByMenuId(menuId);
    }

    @PreAuthorize("hasAuthority('ROLE_WAITER')")
    @PutMapping("recommendation/{recommendationId}")
    ResponseEntity<Recommendation> update(@PathVariable Long recommendationId, @RequestBody Recommendation recommendation) {
        return new ResponseEntity<>(recommendationService.update(recommendationId, recommendation), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_WAITER')")
    @PutMapping("recommendation/{recommendationId}/dish/{dishId}")
    ResponseEntity<Recommendation> removeDishById(@PathVariable Long recommendationId, @PathVariable Long dishId) {
        return new ResponseEntity<>(recommendationService.removeDishById(recommendationId, dishId), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_WAITER')")
    @DeleteMapping("recommendation/{recommendationId}")
    ResponseEntity<Void> deleteRecommendation(@PathVariable Long recommendationId) {
        recommendationService.deleteRecommendation(recommendationId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
