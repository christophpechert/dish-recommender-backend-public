package com.project.dishrecommender.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class KeywordNotFoundException extends RuntimeException{

    public KeywordNotFoundException(Long keywordId) {
        super("Keyword not found with ID: " + keywordId);
    }
}
