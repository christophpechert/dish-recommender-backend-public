package com.project.dishrecommender.exception;

import com.project.dishrecommender.user.entity.UserRole;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class NotAUserRoleException extends RuntimeException{

    public NotAUserRoleException(String role) {
        super(role + " is not a valid user role!");
    }
}
