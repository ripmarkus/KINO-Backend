package com.example.kinoxp.repository.Theatre;

import com.example.kinoxp.model.theatre.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ScreeningRepo extends JpaRepository<Screening, Integer> {
    
    // Custom query to eagerly fetch theatre when loading screening
    // This prevents lazy loading issues when accessing screening.getTheatre()
    @Query("SELECT s FROM Screening s JOIN FETCH s.theatre WHERE s.showId = :id")
    Optional<Screening> findByIdWithTheatre(@Param("id") Integer id);
}
