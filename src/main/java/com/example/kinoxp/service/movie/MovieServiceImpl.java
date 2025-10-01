package com.example.kinoxp.service.movie;

import com.example.kinoxp.model.movie.Movie;
import com.example.kinoxp.repository.mvoie.MovieRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {
    
    private final MovieRepo movieRepo;
    
    public MovieServiceImpl(MovieRepo movieRepo) {
        this.movieRepo = movieRepo;
    }

    @Override
    public boolean existsById(Integer id) {
        return movieRepo.existsById(id);
    }
    
    @Override
    public Optional<Movie> findById(Integer id) {
        return movieRepo.findById(id);
    }
    
    @Override
    public List<Movie> findAll() {
        return movieRepo.findAll();
    }
    
    @Override
    public Movie save(Movie movie) {
        return movieRepo.save(movie);
    }
    
    @Override
    public void deleteById(Integer id) {
        movieRepo.deleteById(id);
    }
}
