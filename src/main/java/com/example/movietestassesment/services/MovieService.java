package com.example.movietestassesment.services;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.movietestassesment.entities.Movie;
import com.example.movietestassesment.models.CreateMovieRequest;
import com.example.movietestassesment.models.MovieResponse;
import com.example.movietestassesment.models.SearchMovieRequest;
import com.example.movietestassesment.models.UpdateMovieRequest;
import com.example.movietestassesment.repositories.MovieRepository;

import jakarta.persistence.criteria.Predicate;


@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;
    
    @Autowired
    private ValidationService validationService;

    @Transactional(readOnly = true)
    public List<MovieResponse> list() {
        List<Movie> movies = movieRepository.findAll(); 
        return movies.stream().map(this::toMovieResponse).toList();
    }

    @Transactional
    public MovieResponse create(CreateMovieRequest request){
        validationService.validate(request);
        
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        
        Movie movie = new Movie();
        movie.setTitle(request.getTitle());
        movie.setDescription(request.getDescription());
        movie.setRating(request.getRating());
        movie.setImage(request.getImage());
        movie.setCreated_at(ts);
        movie.setUpdated_at(ts);


        movieRepository.save(movie); 

        return toMovieResponse(movie);
    }
    
    private MovieResponse toMovieResponse(Movie movie) {
        return MovieResponse.builder()
                            .id(movie.getId())
                            .title(movie.getTitle())
                            .description(movie.getDescription())
                            .rating(movie.getRating())
                            .image(movie.getImage())
                            .created_at(movie.getCreated_at())
                            .updated_at(movie.getUpdated_at())
                            .build();
    }

    @Transactional(readOnly = true)
    public MovieResponse get(int id) {
        Movie movie = movieRepository.findFirstById(id)
                                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found"));
        return toMovieResponse(movie);
    }

    @Transactional
    public MovieResponse update(UpdateMovieRequest request) {
        validationService.validate(request);

        Timestamp ts = new Timestamp(System.currentTimeMillis());

        Movie movie = movieRepository.findFirstById(request.getId())
                                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found")); 
        
        movie.setTitle(request.getTitle());
        movie.setDescription(request.getDescription());
        movie.setRating(request.getRating());
        movie.setImage(request.getImage());
        movie.setCreated_at(ts);
        movie.setUpdated_at(ts);
        movieRepository.save(movie);

        return toMovieResponse(movie);
    }

    @Transactional
    public void delete(int id) {
        Movie movie = movieRepository.findFirstById(id)
                                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found"));
        movieRepository.delete(movie);
    }

    @Transactional(readOnly = true)
    public Page<MovieResponse > search(SearchMovieRequest request) {
        Specification<Movie> specification = (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(builder.like(root.get("title"), "%" + request.getTitle() + "%"));

            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<Movie> movies = movieRepository.findAll(specification, pageable);
        List<MovieResponse> movieResponses = movies.getContent().stream()
                .map(this::toMovieResponse)
                .toList();

        return new PageImpl<>(movieResponses, pageable, movies.getTotalElements());
    }
}
