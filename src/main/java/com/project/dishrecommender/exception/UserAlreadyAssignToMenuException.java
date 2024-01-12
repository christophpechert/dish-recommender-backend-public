package com.project.dishrecommender.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class UserAlreadyAssignToMenuException extends RuntimeException{

    public UserAlreadyAssignToMenuException(String message) {
        super(message);
    }

    public UserAlreadyAssignToMenuException() {
        super();
    }
}
