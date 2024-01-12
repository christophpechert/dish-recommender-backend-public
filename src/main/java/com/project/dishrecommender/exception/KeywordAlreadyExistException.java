package com.project.dishrecommender.exception;

import com.project.dishrecommender.keyword.entity.Keyword;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class KeywordAlreadyExistException extends RuntimeException{

    public KeywordAlreadyExistException(Keyword keyword) {
        super("Keyword already exist with the name: " + keyword.getName());
    }
}
