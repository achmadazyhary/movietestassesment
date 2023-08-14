package com.example.movietestassesment.controllers;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.movietestassesment.entities.Movie;
import com.example.movietestassesment.models.CreateMovieRequest;
import com.example.movietestassesment.models.MovieResponse;
import com.example.movietestassesment.models.WebResponse;
import com.example.movietestassesment.repositories.MovieRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


@SpringBootTest
@AutoConfigureMockMvc
public class MovieControllerTest {

    @Autowired
    private MockMvc mockMVC;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        movieRepository.deleteAll();
    }

    @Test
    void listMovieSuccess() throws Exception {
        for (int i = 0; i < 5; i++) {
            Movie movie = new Movie();
            movie.setTitle(" Pengabdi Setan 2 Comunion");
            movie.setDescription("dalah sebuah film horor Indonesia tahun 2022 yang disutradarai dan\rditulis oleh Joko Anwar sebagai sekuel dari film tahun 2017, Pengabdi\rSetan.");
            movie.setRating((float)7);
            movie.setImage("");
            movieRepository.save(movie);
        }

        mockMVC.perform(
            get("/api/movies")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
            status().isOk()
        ).andDo(result -> {
            WebResponse<List<MovieResponse>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>(){
            });

            assertNull(response.getErrors());
            assertEquals(5, response.getData().size());
        });
    }

    @Test
    void createMovieSuccess() throws Exception {
        CreateMovieRequest request = new CreateMovieRequest();
        request.setTitle("Pengabdi Setan 2 Comunion");
        request.setDescription("dalah sebuah film horor Indonesia tahun 2022 yang disutradarai dan\rditulis oleh Joko Anwar sebagai sekuel dari film tahun 2017, Pengabdi\rSetan.");
        request.setRating(7f);
        request.setImage("");
        
        mockMVC.perform(
            post("/api/movies")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
            status().isOk()
        ).andDo(result -> {
            WebResponse<MovieResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>(){
            });

            assertNull(response.getErrors());
            assertEquals("Pengabdi Setan 2 Comunion", response.getData().getTitle());
            assertEquals("dalah sebuah film horor Indonesia tahun 2022 yang disutradarai dan\rditulis oleh Joko Anwar sebagai sekuel dari film tahun 2017, Pengabdi\rSetan.", response.getData().getDescription());
            assertEquals(7f, response.getData().getRating());
            assertEquals("", response.getData().getImage());

            assertTrue(movieRepository.existsById(response.getData().getId())); 
        });
    }

    @Test
    void getMovieSuccess() throws Exception {
        Movie movie = new Movie();
        movie.setTitle("Pengabdi Setan 2 Comunion");
        movie.setDescription("dalah sebuah film horor Indonesia tahun 2022 yang disutradarai dan\rditulis oleh Joko Anwar sebagai sekuel dari film tahun 2017, Pengabdi\rSetan.");
        movie.setRating((float)7);
        movie.setImage("");
        movieRepository.save(movie);

        mockMVC.perform(
            get("/api/movies/" + movie.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
            status().isOk()
        ).andDo(result -> {
            WebResponse<MovieResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>(){
            });

            assertNull(response.getErrors());

            assertEquals(movie.getId(), response.getData().getId());
            assertEquals(movie.getTitle(), response.getData().getTitle());
            assertEquals(movie.getDescription(), response.getData().getDescription());
            assertEquals(movie.getRating(), response.getData().getRating());
            assertEquals(movie.getImage (), response.getData().getImage());
        });
    }

    @Test
    void updateMovieSuccess() throws Exception {
        Movie movie = new Movie();
        movie.setTitle("Pengabdi Setan 2 Comunion");
        movie.setDescription("dalah sebuah film horor Indonesia tahun 2022 yang disutradarai dan\rditulis oleh Joko Anwar sebagai sekuel dari film tahun 2017, Pengabdi\rSetan.");
        movie.setRating((float)7);
        movie.setImage("");
        movieRepository.save(movie);

        CreateMovieRequest request = new CreateMovieRequest();
        request.setTitle("Pengabdi Setan 2");
        request.setDescription("");
        request.setRating(8.0f);
        request.setImage("");
        
        mockMVC.perform(
            patch("/api/movies/" + movie.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
            status().isOk()
        ).andDo(result -> {
            WebResponse<MovieResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>(){
            });

            assertNull(response.getErrors());

            assertEquals(request.getTitle(), response.getData().getTitle());
            assertEquals(request.getDescription(), response.getData().getDescription());
            assertEquals(request.getRating(), response.getData().getRating());
            assertEquals(request.getImage(), response.getData().getImage());

            assertTrue(movieRepository.existsById(response.getData().getId())); 
        });
    }

    @Test
    void deleteMovieSuccess() throws Exception {
        Movie movie = new Movie();
        movie.setTitle("Pengabdi Setan 2 Comunion");
        movie.setDescription("dalah sebuah film horor Indonesia tahun 2022 yang disutradarai dan\rditulis oleh Joko Anwar sebagai sekuel dari film tahun 2017, Pengabdi\rSetan.");
        movie.setRating((float)7);
        movie.setImage("");
        movieRepository.save(movie);

        mockMVC.perform(
            delete("/api/movies/" + movie.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
            status().isOk()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>(){
            });

            assertNull(response.getErrors());
            assertEquals("OK", response.getData());
        });
    }

    @Test
    void searchNotFound() throws Exception {
        mockMVC.perform(
            get("/api/movies/search")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
            status().isOk()
        ).andDo(result -> {
            WebResponse<List<MovieResponse>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>(){
            });

            assertNull(response.getErrors());
            assertEquals(0, response.getData().size());
            assertEquals(0, response.getPaging().getTotalPage());
            assertEquals(0, response.getPaging().getCurrentPage ());
            assertEquals(10, response.getPaging().getSize());
        });
    }

    @Test
    void searchUsingTitle() throws Exception {
        for (int i = 0; i < 100; i++) {
            Movie movie = new Movie();
            movie.setTitle("test" + i);
            movie.setDescription("dalah sebuah film horor Indonesia tahun 2022 yang disutradarai dan\rditulis oleh Joko Anwar sebagai sekuel dari film tahun 2017, Pengabdi\rSetan.");
            movie.setRating((float)7);
            movie.setImage("");
            movieRepository.save(movie);
        }

        mockMVC.perform(
            get("/api/movies/search")
                .queryParam("title", "Test")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
            status().isOk()
        ).andDo(result -> {
            WebResponse<List<MovieResponse>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>(){
            });

            assertNull(response.getErrors());
            assertEquals(10, response.getData().size());
            assertEquals(10, response.getPaging().getTotalPage());
            assertEquals(0, response.getPaging().getCurrentPage ());
            assertEquals(10, response.getPaging().getSize());
        });
    }


}
