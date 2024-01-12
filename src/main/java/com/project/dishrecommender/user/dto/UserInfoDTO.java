package com.project.dishrecommender.user.dto;

import com.project.dishrecommender.user.entity.UserRole;

import java.time.LocalDateTime;

public record UserInfoDTO(
        Long id,
        String name,
        String email,
        UserRole role,
        LocalDateTime created,
        LocalDateTime lastChange) {
}
