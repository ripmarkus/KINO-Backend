package com.example.kinoxp.service.theatre;

import com.example.kinoxp.model.theatre.Theatre;

import java.util.List;
import java.util.Optional;

public interface TheatreService {
    boolean existsById(Integer id);
    Optional<Theatre> findById(Integer id);
    List<Theatre> findAll();
    Theatre save(Theatre theatre);
    void deleteById(Integer id);
}

