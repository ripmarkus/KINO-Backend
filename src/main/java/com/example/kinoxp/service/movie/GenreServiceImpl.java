package com.example.kinoxp.service.movie;

import com.example.kinoxp.model.movie.Genre;
// TODO: Create GenreRepo when needed
// import com.example.kinoxp.repository.movie.GenreRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenreServiceImpl implements GenreService {
    
    // TODO: Add GenreRepo dependency injection when GenreRepo is created
    // private final GenreRepo genreRepo;
    
    // public GenreServiceImpl(GenreRepo genreRepo) {
    //     this.genreRepo = genreRepo;
    // }

    @Override
    public boolean existsById(Integer id) {
        // TODO: Implement when GenreRepo is available
        throw new UnsupportedOperationException("GenreRepo not yet implemented");
    }
    
    @Override
    public Optional<Genre> findById(Integer id) {
        // TODO: Implement when GenreRepo is available
        throw new UnsupportedOperationException("GenreRepo not yet implemented");
    }

    @Override
    public List<Genre> findAll() {
        // TODO: Implement when GenreRepo is available
        throw new UnsupportedOperationException("GenreRepo not yet implemented");
    }

    @Override
    public Genre save(Genre genre) {
        // TODO: Implement when GenreRepo is available
        throw new UnsupportedOperationException("GenreRepo not yet implemented");
    }

    @Override
    public void deleteById(Integer id) {
        // TODO: Implement when GenreRepo is available
        throw new UnsupportedOperationException("GenreRepo not yet implemented");
    }
}
