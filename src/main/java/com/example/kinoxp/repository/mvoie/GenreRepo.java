package com.example.kinoxp.repository.mvoie;

import com.example.kinoxp.model.movie.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepo extends JpaRepository<Genre, Integer> {
}