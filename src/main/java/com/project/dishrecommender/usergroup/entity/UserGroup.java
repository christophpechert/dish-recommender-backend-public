package com.project.dishrecommender.usergroup.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.dishrecommender.dish.entity.Dish;
import com.project.dishrecommender.invite.entity.Invite;
import com.project.dishrecommender.keyword.entity.Keyword;
import com.project.dishrecommender.menu.entity.Menu;
import com.project.dishrecommender.recommendation.entity.Recommendation;
import com.project.dishrecommender.user.entity.UserInfo;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
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
public class UserGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 4)
    @Column(unique = true)
    private String name;

    @Column(nullable = false)
    private LocalDateTime created;
    private LocalDateTime lastChange;

    @JsonIgnore
    @OneToMany(mappedBy = "userGroup", cascade = CascadeType.ALL)
    private List<UserInfo> userInfos = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "userGroup")
    private List<Menu> menus = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "userGroup")
    private List<Dish> dishes = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "userGroup")
    private List<Invite> invites = new ArrayList<>();

    @JsonIgnore
    @OrderBy("name")
    @OneToMany(mappedBy = "userGroup")
    private List<Keyword> keywords = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "userGroup", cascade = CascadeType.ALL)
    private List<Recommendation> recommendations = new ArrayList<>();
}
