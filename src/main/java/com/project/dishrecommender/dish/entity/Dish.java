package com.project.dishrecommender.dish.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.dishrecommender.preparation.entity.Preparation;
import com.project.dishrecommender.keyword.entity.Keyword;
import com.project.dishrecommender.location.Location;
import com.project.dishrecommender.menu.entity.Menu;
import com.project.dishrecommender.imageData.entity.ImageData;
import com.project.dishrecommender.recommendation.entity.Recommendation;
import com.project.dishrecommender.usergroup.entity.UserGroup;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
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
public class Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    private String description;
    private String comment;

    @NotNull
    @Column(nullable = false)
    private DishType dishType;

    @NotNull
    @Column(nullable = false)
    private DishCookingTimeCategory dishCookingTimeCategory;

    @NotNull
    @Column(nullable = false)
    private DishDietaryCategory dishDietaryCategory;

    @NotNull
    @Column(nullable = false)
    private DishCaloricCategory dishCaloricCategory;

    @PastOrPresent
    @Column(nullable = false)
    private LocalDateTime created;

    @PastOrPresent
    private LocalDateTime lastChange;

    @ElementCollection
    @CollectionTable(name = "Dish_Location", joinColumns = @JoinColumn(name = "dish_id"))
    @AttributeOverrides({
            @AttributeOverride(name = "locationType", column = @Column(name = "location_type")),
            @AttributeOverride(name = "link", column = @Column(name = "link")),
            @AttributeOverride(name = "cookbook", column = @Column(name = "cookbook")),
            @AttributeOverride(name = "page", column = @Column(name = "page"))
    })
    private List<Location> locations = new ArrayList<>();

    @ManyToMany()
    @JoinTable(
            name = "Dish_Menu",
            joinColumns = @JoinColumn(name = "dish_id"),
            inverseJoinColumns = @JoinColumn(name = "menu_id"))
    private List<Menu> menus = new ArrayList<>();

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "userGroup_id", nullable = false)
    private UserGroup userGroup;

    @OrderBy("cooked DESC")
    @OneToMany(mappedBy = "dish", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Preparation> preparations = new ArrayList<>();

    @ManyToMany()
    @JoinTable(
            name = "Dish_Keyword",
            joinColumns = @JoinColumn(name = "dish_id"),
            inverseJoinColumns = @JoinColumn(name = "keyword_id"))
    private List<Keyword> keywords = new ArrayList<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "dishes")
    private List<Recommendation> recommendations = new ArrayList<>();

    @OneToMany(mappedBy = "dish")
    private List<ImageData> imageData = new ArrayList<>();
}
