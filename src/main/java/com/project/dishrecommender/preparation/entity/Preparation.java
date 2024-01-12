package com.project.dishrecommender.preparation.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.dishrecommender.dish.entity.Dish;
import com.project.dishrecommender.user.entity.UserInfo;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import java.time.LocalDateTime;
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Preparation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Cooked must be not null")
    @PastOrPresent(message = "Date must be present or in past")
    @Column(nullable = false)
    private LocalDateTime cooked;


    @Column(nullable = false)
    @Min(value = 1, message = "Rating must be equal or greater then 1")
    @Max(value = 5, message = "Rating must be equal or less then 5")
    private Integer rating;

    private String comment;

    @Column(nullable = false)
    private LocalDateTime created;
    private LocalDateTime lastChange;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "dish_id", nullable = false)
    private Dish dish;

    @ManyToOne
    @JoinColumn(name = "userInfo_id")
    private UserInfo userInfo;
}
