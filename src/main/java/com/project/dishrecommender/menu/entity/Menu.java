package com.project.dishrecommender.menu.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.dishrecommender.dish.entity.Dish;
import com.project.dishrecommender.recommendation.entity.Recommendation;
import com.project.dishrecommender.usergroup.entity.UserGroup;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    private String description;

    @Column(nullable = false)
    private LocalDateTime created;
    private LocalDateTime lastChange;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "userGroup_id", nullable = false)
    private UserGroup userGroup;

    @JsonIgnore
    @ManyToMany(mappedBy = "menus")
    private List<Dish> dishes = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "menu")
    private List<Recommendation> recommendations = new ArrayList<>();
}
