package com.project.dishrecommender.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class DishAlreadyAssignToMenuException extends RuntimeException{

    public DishAlreadyAssignToMenuException(String message) {
        super(message);
    }

    public DishAlreadyAssignToMenuException() {
        super();
    }
}
