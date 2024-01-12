package com.project.dishrecommender.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class MenuAlreadyExistException extends RuntimeException{

    public MenuAlreadyExistException(String message) {
        super(message);
    }

    public MenuAlreadyExistException() {
        super();
    }
}
