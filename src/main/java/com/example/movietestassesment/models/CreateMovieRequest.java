package com.example.movietestassesment.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateMovieRequest {

    @NotBlank
    @Size(max = 255)
    private String title;

    @Size(max = 255)
    private String description;

    private Float rating;

    @Size(max = 255)
    private String image;
}
