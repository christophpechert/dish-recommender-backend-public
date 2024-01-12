package com.project.dishrecommender.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AuthPasswordEncoder {

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
