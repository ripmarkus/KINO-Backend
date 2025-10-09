package com.example.kinoxp.service.movie;

import com.example.kinoxp.DTO.movie.MovieRequest;
import com.example.kinoxp.model.movie.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieService {
    boolean existsById(Integer id);
    Optional<Movie> findById(Integer id);
    List<Movie> findAll();
    Movie save(Movie movie);
    void deleteById(Integer id);
    Movie getRequiredMovie(Integer id);
    Movie createMovie(MovieRequest movieRequest);
}
