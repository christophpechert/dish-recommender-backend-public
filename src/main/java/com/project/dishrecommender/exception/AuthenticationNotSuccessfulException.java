package com.project.dishrecommender.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class AuthenticationNotSuccessfulException extends RuntimeException{

    public AuthenticationNotSuccessfulException(String message) {
        super(message);
    }

    public AuthenticationNotSuccessfulException() {
        super();
    }
}
