package com.project.dishrecommender.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class NoDishesForRecommendationException extends RuntimeException{

    public NoDishesForRecommendationException() {
        super("No dishes for recommendation");
    }

}
