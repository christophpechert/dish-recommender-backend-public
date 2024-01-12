package com.project.dishrecommender.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class RecommendationNoMemberException extends RuntimeException{

    public RecommendationNoMemberException(Long recommendationId) {
        super("Recommendation not a member of User Group with ID: " + recommendationId);
    }

}
