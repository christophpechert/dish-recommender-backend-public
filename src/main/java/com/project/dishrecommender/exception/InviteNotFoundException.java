package com.project.dishrecommender.exception;

import com.project.dishrecommender.invite.entity.Invite;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class InviteNotFoundException extends RuntimeException{

    public InviteNotFoundException(Long  inviteId) {
        super("Invite not found with ID: " + inviteId);
    }
}
