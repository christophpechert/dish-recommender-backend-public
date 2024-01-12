package com.project.dishrecommender.user;


import com.project.dishrecommender.user.entity.UserInfo;
import com.project.dishrecommender.user.entity.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserInfoDetails implements UserDetails {

    private final String name;
    private final String password;
    private final List<GrantedAuthority> authorities;

    private final boolean isAuthenticated;

    public UserInfoDetails(UserInfo userInfo) {
        name = userInfo.getName();
        password = userInfo.getPassword();
        authorities = assignAuthorities(userInfo.getRole())
                .stream()
                .map(e -> new SimpleGrantedAuthority(e.toString())).collect(Collectors.toList());
        isAuthenticated = userInfo.getIsActivated();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isAuthenticated;
    }

    private List<UserRole> assignAuthorities(UserRole role) {
        List<UserRole> userRights = new ArrayList<>();

        if (role == UserRole.ROLE_OWNER) {
            userRights.add(UserRole.ROLE_OWNER);
            userRights.add(UserRole.ROLE_CHEF);
            userRights.add(UserRole.ROLE_WAITER);
            userRights.add(UserRole.ROLE_GUEST);
            userRights.add(UserRole.ROLE_NEWBIE);
        }

        if (role == UserRole.ROLE_CHEF) {
            userRights.add(UserRole.ROLE_CHEF);
            userRights.add(UserRole.ROLE_WAITER);
            userRights.add(UserRole.ROLE_GUEST);
            userRights.add(UserRole.ROLE_NEWBIE);
        }

        if (role == UserRole.ROLE_WAITER) {
            userRights.add(UserRole.ROLE_WAITER);
            userRights.add(UserRole.ROLE_GUEST);
            userRights.add(UserRole.ROLE_NEWBIE);
        }

        if (role == UserRole.ROLE_GUEST) {
            userRights.add(UserRole.ROLE_GUEST);
            userRights.add(UserRole.ROLE_NEWBIE);
        }

        if (role == UserRole.ROLE_NEWBIE) {
            userRights.add(UserRole.ROLE_NEWBIE);
        }

        return userRights;
    }
}
