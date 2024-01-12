package com.project.dishrecommender.user;

import com.project.dishrecommender.exception.*;
import com.project.dishrecommender.security.AuthPasswordEncoder;
import com.project.dishrecommender.user.entity.UserInfo;
import com.project.dishrecommender.user.entity.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserInfoService implements UserDetailsService {
    private final UserInfoRepository userInfoRepository;
    private final AuthPasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> userDetail = userInfoRepository.findByName(username);
        return userDetail.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
    }

    public UserInfo add(UserInfo userInfo) {
        userInfoRepository.findByName(userInfo.getName())
                .ifPresent(e -> {throw new UserAlreadyExistException(e);});

        userInfoRepository.findByEmail(userInfo.getEmail())
                .ifPresent(e -> {throw new EmailAlreadyExistException(e);});

        userInfo.setRole(UserRole.ROLE_NEWBIE);
        userInfo.setIsActivated(false);
        userInfo.setPassword(encoder.passwordEncoder().encode(userInfo.getPassword()));
        userInfo.setCreated(LocalDateTime.now());
        return save(userInfo);
    }

    public UserInfo save(UserInfo user) {
        return userInfoRepository.save(user);
    }

    public UserInfo retrieveByAuth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userInfoRepository.findByName(authentication.getName())
                .orElseThrow(() -> new UserNotFoundException("Username not found: " + authentication.getName()));
    }

    public UserInfo retrieveById(Long userInfoId) {
        return userInfoRepository.findById(userInfoId)
                .orElseThrow(() -> new UserNotFoundException("User ID not found: " + userInfoId));
    }

    public UserInfo changeRoleById(Long userInfoId, UserRole userRole) {
        UserInfo authenticatedUser = retrieveByAuth();
        UserInfo userToChange = retrieveById(userInfoId);

        if (authenticatedUser.getRole() != UserRole.ROLE_OWNER || userToChange.getRole() == UserRole.ROLE_OWNER) {
            throw new UserNoAuthoritiesException(authenticatedUser.getId());
        }

        if (userRole == UserRole.ROLE_NEWBIE) {
            throw new UserRoleNotPossibleException(userRole);
        }

        userToChange.setRole(userRole);
        userToChange.setLastChange(LocalDateTime.now());

        return save(userToChange);
    }
}
