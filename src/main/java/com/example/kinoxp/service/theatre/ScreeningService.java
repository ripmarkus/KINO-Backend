package com.example.kinoxp.service.theatre;

import com.example.kinoxp.model.theatre.Screening;

import java.util.List;
import java.util.Optional;

public interface ScreeningService {
    boolean existsById(Integer id);
    Optional<Screening> findById(Integer id);
    List<Screening> findAll();
    Screening save(Screening screening);
    void deleteById(Integer id);
    Screening getRequiredScreening(Integer id);
}
