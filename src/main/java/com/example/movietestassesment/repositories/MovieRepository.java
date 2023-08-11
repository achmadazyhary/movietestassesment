package com.example.movietestassesment.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.movietestassesment.entities.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
    
    Optional<Movie> findFirstById(int id);
    
}
