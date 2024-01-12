package com.project.dishrecommender.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class RecipeImageDataNotFoundException extends RuntimeException{

    public RecipeImageDataNotFoundException(Long recipeImageDataId) {
        super("Recipe image data not found with Id: " + recipeImageDataId);
    }

    public RecipeImageDataNotFoundException() {
        super();
    }
}
