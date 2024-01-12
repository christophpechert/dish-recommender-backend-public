package com.project.dishrecommender.keyword.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.dishrecommender.dish.entity.Dish;
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
public class Keyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @Column(nullable = false)
    private LocalDateTime created;
    private LocalDateTime lastChange;

    @JsonIgnore
    @ManyToMany(mappedBy = "keywords")
    private List<Dish> dishes = new ArrayList<>();

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "userGroup_id", nullable = false)
    private UserGroup userGroup;
}
