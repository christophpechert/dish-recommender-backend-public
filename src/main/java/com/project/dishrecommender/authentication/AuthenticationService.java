package com.project.dishrecommender.authentication;

import com.project.dishrecommender.exception.AuthenticationNotSuccessfulException;
import com.project.dishrecommender.security.JwtService;
import com.project.dishrecommender.user.entity.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AuthenticationService {
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse checkAuthentication(AuthenticationRequest authenticationRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));

        if (!authentication.isAuthenticated()) {
            throw new AuthenticationNotSuccessfulException("Authentication not successful");
        }

        List<UserRole> roles = authentication.getAuthorities()
                .stream()
                .map((e) -> UserRole.valueOf(e.getAuthority()))
                .toList();

        String bearerToken = jwtService.generateToken(authenticationRequest.getUsername());

        return new AuthenticationResponse(bearerToken, roles);
    }
}
