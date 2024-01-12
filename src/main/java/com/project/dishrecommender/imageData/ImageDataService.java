package com.project.dishrecommender.imageData;


import com.project.dishrecommender.amazonS3.AmazonS3Object;
import com.project.dishrecommender.amazonS3.AmazonS3Service;
import com.project.dishrecommender.dish.DishService;
import com.project.dishrecommender.dish.entity.Dish;
import com.project.dishrecommender.exception.RecipeImageDataNotFoundException;
import com.project.dishrecommender.imageData.entity.ImageData;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@AllArgsConstructor
public class ImageDataService {
    private final ImageDataRepository imageDataRepository;
    private final DishService dishService;
    private final AmazonS3Service amazonS3Service;

    public ImageData add(Long dishId, MultipartFile file) {
        Dish dish = dishService.retrieveById(dishId);

        AmazonS3Object amazonS3Object = amazonS3Service.upload(file);

        return imageDataRepository.save(new ImageData(null, amazonS3Object.key(), amazonS3Object.bucket(), dish));
    }

    public List<ImageData> retrieveAllFromDish(Long dishId) {
        return imageDataRepository.findByDishId(dishId);
    }

    public void delete(Long imageDataId) {
        ImageData imageData = imageDataRepository.findById(imageDataId)
                .orElseThrow(() -> new RecipeImageDataNotFoundException(imageDataId));

        amazonS3Service.delete(new AmazonS3Object(imageData.getBucket(), imageData.getFileName()));

        imageDataRepository.deleteById(imageDataId);
    }
}
