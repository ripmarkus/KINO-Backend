package com.example.kinoxp.RestController;

import com.example.kinoxp.DTO.movie.MovieRequest;
import com.example.kinoxp.model.movie.Movie;
import com.example.kinoxp.model.movie.Genre;
import com.example.kinoxp.service.movie.MovieService;
import com.example.kinoxp.service.movie.GenreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieGenreRestController {

    private final MovieService movieService;
    private final GenreService genreService;

    public MovieGenreRestController(MovieService movieService, GenreService genreService) {
        this.movieService = movieService;
        this.genreService = genreService;
    }

    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies() {
        return ResponseEntity.ok(movieService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable Integer id) {
        return movieService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody MovieRequest movieRequest) {
        Movie savedMovie = movieService.createMovie(movieRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMovie);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable Integer id, @RequestBody Movie movie) {
        if (!movieService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        movie.setMovieId(id);
        return ResponseEntity.ok(movieService.save(movie));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Integer id) {
        if (!movieService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        movieService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/genres")
    public ResponseEntity<List<Genre>> getAllGenres() {
        return ResponseEntity.ok(genreService.findAll());
    }
}
