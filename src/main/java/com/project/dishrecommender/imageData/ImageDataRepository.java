package com.project.dishrecommender.imageData;

import com.project.dishrecommender.imageData.entity.ImageData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageDataRepository extends JpaRepository<ImageData, Long> {

    List<ImageData> findByDishId(Long dishId);
}
