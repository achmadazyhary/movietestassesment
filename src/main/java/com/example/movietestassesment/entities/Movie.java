package com.example.movietestassesment.entities;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Anotasi ini menghasilkan getter, setter, equals, hashCode, dan toString
@AllArgsConstructor // Anotasi ini menghasilkan constructor dengan semua parameter
@NoArgsConstructor // Anotasi ini menghasilkan constructor default tanpa parameter
@Entity
@Table(name = "movie")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title")
    private String title;
    
    private String description;
    
    private Float rating;
    
    private String image;
    
    private Timestamp created_at;
    
    private Timestamp updated_at;
}
