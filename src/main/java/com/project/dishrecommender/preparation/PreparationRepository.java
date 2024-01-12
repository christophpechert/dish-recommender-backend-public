package com.project.dishrecommender.preparation;

import com.project.dishrecommender.preparation.entity.Preparation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PreparationRepository extends JpaRepository<Preparation, Long> {

    List<Preparation> findByDishIdOrderByCookedDesc(Long dishId);
}
