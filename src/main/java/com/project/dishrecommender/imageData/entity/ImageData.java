package com.project.dishrecommender.imageData.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.dishrecommender.dish.entity.Dish;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ImageData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String bucket;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "dish_id", nullable = false)
    private Dish dish;
}
