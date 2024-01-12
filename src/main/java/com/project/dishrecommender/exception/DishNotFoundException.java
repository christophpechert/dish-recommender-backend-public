package com.project.dishrecommender.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class DishNotFoundException extends RuntimeException{

    public DishNotFoundException(String message) {
        super(message);
    }

    public DishNotFoundException() {
        super();
    }
}
