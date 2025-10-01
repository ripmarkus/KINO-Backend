package com.example.kinoxp.service.movie;

import com.example.kinoxp.model.movie.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreService {
    boolean existsById(Integer id);
    Optional<Genre> findById(Integer id);
    List<Genre> findAll();
    Genre save(Genre genre);
    void deleteById(Integer id);
}