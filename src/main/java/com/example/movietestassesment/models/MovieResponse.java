package com.example.movietestassesment.models;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieResponse {

    private Integer id;

    private String title;

    private String description;

    private Float rating;

    private String image;

    private Timestamp created_at;
    
    private Timestamp updated_at;
}
