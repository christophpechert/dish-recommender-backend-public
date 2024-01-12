package com.project.dishrecommender.user;

import com.project.dishrecommender.user.dto.UserInfoMapper;
import com.project.dishrecommender.user.dto.UserInfoWithUserGroupDTO;
import com.project.dishrecommender.user.entity.UserInfo;
import com.project.dishrecommender.user.entity.UserRole;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-management")
@AllArgsConstructor
public class UserInfoController {
    private final UserInfoService userInfoService;
    private final UserInfoMapper userInfoMapper;

    @PostMapping("/user")
    UserInfo add(@RequestBody @Valid UserInfo userInfo) {
        return userInfoService.add(userInfo);
    }

    @GetMapping("/user")
    ResponseEntity<UserInfoWithUserGroupDTO> retrieveUserByAuth() {
        UserInfoWithUserGroupDTO userInfoDTO = userInfoMapper.userInfoToUserInfoWithUserGroupDTO(userInfoService.retrieveByAuth());
        return new ResponseEntity<>(userInfoDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_CHEF')")
    @PutMapping("/user/{userInfoId}/user-role/{role}")
    ResponseEntity<UserInfo> changeRoleByIdAndRole(@PathVariable Long userInfoId, @PathVariable UserRole role) {
        return new ResponseEntity<>(userInfoService.changeRoleById(userInfoId, role), HttpStatus.OK);
    }
}
