package com.example.kinoxp.DTO.movie;

import com.example.kinoxp.model.movie.Genre;
import lombok.*;


@Getter
@Setter
@Data
public class MovieRequest {

    private String title;
    private Integer ageLimit;
    private Integer duration;
    private String description;
    private GenreRequest genre;

}
