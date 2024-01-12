package com.project.dishrecommender.exception;

import com.project.dishrecommender.authentication.AuthenticationRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class UserNotActivatedException extends RuntimeException{

    public UserNotActivatedException(AuthenticationRequest authenticationRequest) {
        super("User not activated with name: " + authenticationRequest.getUsername());
    }
}
