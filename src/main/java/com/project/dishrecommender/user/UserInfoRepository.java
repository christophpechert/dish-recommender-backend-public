package com.project.dishrecommender.user;

import com.project.dishrecommender.user.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {

    Optional<UserInfo> findByName(String username);

    Optional<UserInfo> findByEmail(String email);
}
