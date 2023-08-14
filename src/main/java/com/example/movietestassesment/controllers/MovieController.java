package com.example.movietestassesment.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.movietestassesment.models.CreateMovieRequest;
import com.example.movietestassesment.models.MovieResponse;
import com.example.movietestassesment.models.PagingResponses;
import com.example.movietestassesment.models.SearchMovieRequest;
import com.example.movietestassesment.models.UpdateMovieRequest;
import com.example.movietestassesment.models.WebResponse;
import com.example.movietestassesment.services.MovieService;

@RestController
public class MovieController {
    
    @Autowired
    private MovieService movieService;

    @GetMapping(
        path = "/api/movies",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<MovieResponse>> list() {
        List<MovieResponse> list = movieService.list();
        return WebResponse.<List<MovieResponse>>builder().data(list).build();
    }

    @PostMapping(
        path = "/api/movies",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<MovieResponse> create(@RequestBody CreateMovieRequest request) {
        MovieResponse movieResponse = movieService.create(request);
        return WebResponse.<MovieResponse>builder().data(movieResponse).build();
    }

    @GetMapping(
        path = "/api/movies/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<MovieResponse> get(@PathVariable("id") int id) {
        MovieResponse movieResponse = movieService.get(id);
        return WebResponse.<MovieResponse>builder().data(movieResponse).build();
    }

    @PatchMapping(
        path = "/api/movies/{id}",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<MovieResponse> update(@RequestBody UpdateMovieRequest request,
                                            @PathVariable("id") int id) {
        request.setId(id);
        MovieResponse movieResponse = movieService.update(request);
        return WebResponse.<MovieResponse>builder().data(movieResponse).build();
    }

    @DeleteMapping(
        path = "/api/movies/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> delete(@PathVariable("id") int id) {
        movieService.delete(id);
        return WebResponse.<String>builder().data("OK").build();
    } 

    @GetMapping(
        path = "/api/movies/search",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<MovieResponse>> search(@RequestParam(value = "title", required = false) String title,
                                                @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {
        SearchMovieRequest request = SearchMovieRequest.builder()
                .page(page)
                .size(size)
                .title(title)
                .build();
        
        Page<MovieResponse> movieResponses = movieService.search(request);
        return WebResponse.<List<MovieResponse>>builder()
                .data(movieResponses.getContent())
                .paging(PagingResponses.builder()
                        .currentPage(movieResponses.getNumber())
                        .totalPage(movieResponses.getTotalPages())
                        .size(movieResponses.getSize())
                        .build())
                .build();
    }
}
