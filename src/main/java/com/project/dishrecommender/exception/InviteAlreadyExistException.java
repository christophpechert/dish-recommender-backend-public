package com.project.dishrecommender.exception;

import com.project.dishrecommender.invite.entity.Invite;
import com.project.dishrecommender.keyword.entity.Keyword;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class InviteAlreadyExistException extends RuntimeException{

    public InviteAlreadyExistException(Invite invite) {
        super("Invite already exist with status: " + invite.getStatus());
    }
}
