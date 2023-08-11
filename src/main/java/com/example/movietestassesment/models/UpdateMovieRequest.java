package com.example.movietestassesment.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateMovieRequest {

    @JsonIgnore
    @NotNull
    private int id;

    @NotBlank
    @Size(max = 255)
    public String title;

    @Size(max = 255)
    public String description;

    private Float rating;

    @Size(max = 255)
    private String image;
}
