package com.example.kinoxp.service.movie;

import com.example.kinoxp.model.movie.Genre;
import com.example.kinoxp.repository.mvoie.GenreRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenreServiceImpl implements GenreService {
    
    private final GenreRepo genreRepo;
    
    public GenreServiceImpl(GenreRepo genreRepo) {
        this.genreRepo = genreRepo;
    }

    @Override
    public boolean existsById(Integer id) {
        return genreRepo.existsById(id);
    }
    
    @Override
    public Optional<Genre> findById(Integer id) {
        return genreRepo.findById(id);
    }
    
    @Override
    public List<Genre> findAll() {
        return genreRepo.findAll();
    }
    
    @Override
    public Genre save(Genre genre) {
        return genreRepo.save(genre);
    }
    
    @Override
    public void deleteById(Integer id) {
        genreRepo.deleteById(id);
    }
}
