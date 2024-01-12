package com.project.dishrecommender.authentication;

import com.project.dishrecommender.user.entity.UserRole;

import java.util.List;

public record AuthenticationResponse(
        String bearerToken,
        List<UserRole> roles) {
}
