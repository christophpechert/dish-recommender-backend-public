package com.project.dishrecommender.exception;

import com.project.dishrecommender.invite.entity.Invite;
import com.project.dishrecommender.user.entity.UserRole;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class UserRoleNotPossibleException extends RuntimeException{

    public UserRoleNotPossibleException(UserRole userRole) {
        super(userRole + " is not allowed here!");
    }
}
