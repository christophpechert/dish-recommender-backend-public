package com.project.dishrecommender.invite.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.dishrecommender.user.entity.UserInfo;
import com.project.dishrecommender.usergroup.entity.UserGroup;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Invite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    @Column(nullable = false)
    private LocalDateTime created;
    private LocalDateTime lastChange;

    @Column(nullable = false)
    private InviteStatus status;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userInfo_id", nullable = false)
    private UserInfo userInfo;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "userGroup_id", nullable = false)
    private UserGroup userGroup;
}
