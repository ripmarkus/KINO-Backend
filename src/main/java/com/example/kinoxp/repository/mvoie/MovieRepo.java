package com.example.kinoxp.repository;

import com.example.kinoxp.model.movie.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepo extends JpaRepository<Movie,Integer> {
}
