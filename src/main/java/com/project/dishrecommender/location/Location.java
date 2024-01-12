package com.project.dishrecommender.location;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Location {

    private LocationType locationType;
    private String link;
    private String cookbook;
    private Integer page;
}
