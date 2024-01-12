package com.project.dishrecommender.preparation;

import com.project.dishrecommender.preparation.entity.Preparation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/preparation-management")
@AllArgsConstructor
public class PreparationController {
    private final PreparationService preparationService;

    @PostMapping("/preparation/dish/{dishId}")
    ResponseEntity<Preparation> add(@PathVariable Long dishId, @Valid @RequestBody Preparation preparation) {
        return new ResponseEntity<>(preparationService.add(dishId, preparation), HttpStatus.CREATED);
    }

    @GetMapping("/preparation/dish/{dishId}")
    ResponseEntity<List<Preparation>> retrieveByDishId(@PathVariable Long dishId) {
        return new ResponseEntity<>(preparationService.retrieveAllFromDish(dishId), HttpStatus.OK);
    }
}
