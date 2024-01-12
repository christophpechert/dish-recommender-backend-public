package com.project.dishrecommender.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class RecommendationNotFoundException extends RuntimeException{

    public RecommendationNotFoundException(Long recommendationId) {
        super("Recommendation not found with Id: " + recommendationId);
    }
}
