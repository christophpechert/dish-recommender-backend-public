package com.project.dishrecommender.recommendation.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.dishrecommender.dish.entity.Dish;
import com.project.dishrecommender.keyword.entity.Keyword;
import com.project.dishrecommender.menu.entity.Menu;
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
public class Recommendation {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDateTime created;
    private LocalDateTime lastChange;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToMany()
    @JoinTable(
            name = "Recommendation_Dish",
            joinColumns = @JoinColumn(name = "recommendation_id"),
            inverseJoinColumns = @JoinColumn(name = "dish_id"))
    private List<Dish> dishes = new ArrayList<>();

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "userGroup_id", nullable = false)
    private UserGroup userGroup;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;
}
