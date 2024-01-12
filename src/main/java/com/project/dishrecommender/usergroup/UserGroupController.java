package com.project.dishrecommender.usergroup;

import com.project.dishrecommender.usergroup.dto.UserGroupMapper;
import com.project.dishrecommender.usergroup.dto.UserGroupWithMenusAndKeywordsDTO;
import com.project.dishrecommender.usergroup.dto.UserGroupWithUserInfosDTO;
import com.project.dishrecommender.usergroup.entity.UserGroup;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user-group-management")
@AllArgsConstructor
public class UserGroupController {
    private final UserGroupService userGroupService;
    private final UserGroupMapper userGroupMapper;

    @PostMapping("/user-group")
    ResponseEntity<UserGroup> add(@Valid @RequestBody UserGroup userGroup) {
        return new ResponseEntity<>(userGroupService.add(userGroup), HttpStatus.CREATED);
    }

    @GetMapping("/user-group/{userGroupId}")
    ResponseEntity<UserGroup> retrieveById(@PathVariable Long userGroupId) {
        return new ResponseEntity<>(userGroupService.retrieveById(userGroupId), HttpStatus.OK);
    }

    @GetMapping("/user-group/user")
    ResponseEntity<UserGroupWithUserInfosDTO> retrieveWithAllUsers() {
        UserGroup userGroup = userGroupService.retrieveFromUser();
        UserGroupWithUserInfosDTO userGroupDTO = userGroupMapper.toUserGroupWithUsers(userGroup);
        return new ResponseEntity<>(userGroupDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_OWNER')")
    @PutMapping("/user-group/user/{userInfoId}")
    ResponseEntity<UserGroupWithUserInfosDTO> removeUserById(@PathVariable Long userInfoId) {
        UserGroupWithUserInfosDTO userGroup = userGroupMapper.toUserGroupWithUsers(userGroupService.removeByUserInfoId(userInfoId));
        return new ResponseEntity<>(userGroup, HttpStatus.OK);
    }

    @GetMapping("/user-group")
    ResponseEntity<UserGroupWithMenusAndKeywordsDTO> retrieveWithMenusAndKeywords() {
        return new ResponseEntity<>(userGroupMapper.ToUserGroupWithMenusAndKeywords(userGroupService.retrieveFromUser()), HttpStatus.OK);
    }

    @GetMapping("/user-group/search")
    ResponseEntity<List<UserGroup>> retrieveBySearch(@RequestParam String searchWord) {
        return new ResponseEntity<>(userGroupService.retrieveBySearch(searchWord), HttpStatus.OK);
    }

}
