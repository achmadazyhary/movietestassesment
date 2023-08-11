package com.example.movietestassesment.services;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.movietestassesment.entities.Movie;
import com.example.movietestassesment.models.CreateMovieRequest;
import com.example.movietestassesment.models.MovieResponse;
import com.example.movietestassesment.models.UpdateMovieRequest;
import com.example.movietestassesment.repositories.MovieRepository;


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
}
