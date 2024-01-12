package com.project.dishrecommender.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.dishrecommender.invite.entity.Invite;
import com.project.dishrecommender.preparation.entity.Preparation;
import com.project.dishrecommender.usergroup.entity.UserGroup;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    @Column(unique = true)
    private String name;

    @Email
    @NotEmpty
    @NotNull
    @Column(unique = true)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "Password must not be empty!")
    @NotEmpty
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private UserRole role;

    @Column(nullable = false)
    private LocalDateTime created;
    private LocalDateTime lastChange;

    @Column(nullable = false)
    private Boolean isActivated;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "userGroup_id")
    private UserGroup userGroup;

    @JsonIgnore
    @OneToMany(mappedBy = "userInfo")
    private List<Invite> invites = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "userInfo")
    private List<Preparation> preparations = new ArrayList<>();
}
