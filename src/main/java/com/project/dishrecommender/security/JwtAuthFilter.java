package com.project.dishrecommender.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.dishrecommender.exception.ErrorDetails;
import com.project.dishrecommender.user.UserInfoService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

@Component
@AllArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private JwtService jwtService;
    private UserInfoService userDetailsService;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            username = jwtService.extractUsername(token);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (jwtService.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } else {
                ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), "Token expired", "Your token has expired");
                createUnauthorizedResponse(response, errorDetails);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private void createUnauthorizedResponse(HttpServletResponse response, ErrorDetails errorDetails) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        String json = objectMapper.writeValueAsString(errorDetails);
        PrintWriter writer = response.getWriter();
        writer.write(json);
    }
}
