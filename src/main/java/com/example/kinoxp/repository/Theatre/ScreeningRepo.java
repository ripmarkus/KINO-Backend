package com.example.kinoxp.repository.Theatre;

import com.example.kinoxp.model.theatre.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ScreeningRepo extends JpaRepository<Screening,Integer> {

    public Screening findById(int id);
    
    // Find screenings between two dates
    List<Screening> findByStartTimeBetween(LocalDateTime startTime, LocalDateTime endTime);
    
    // Find screenings for a specific theatre between two dates
    List<Screening> findByTheatreTheatreIdAndStartTimeBetween(
        Integer theatreId, LocalDateTime startTime, LocalDateTime endTime);
    
    // Find screenings for a specific theatre
    List<Screening> findByTheatreTheatreId(Integer theatreId);
    
    // Find screenings ordered by start time
    @Query("SELECT s FROM Screening s ORDER BY s.startTime ASC")
    List<Screening> findAllOrderByStartTime();
    
    // Find overlapping screenings for a theatre (useful for conflict detection)
    @Query("SELECT s FROM Screening s WHERE s.theatre.theatreId = :theatreId " +
           "AND s.startTime < :endTime AND s.endTime > :startTime")
    List<Screening> findOverlappingScreenings(
        @Param("theatreId") Integer theatreId,
        @Param("startTime") LocalDateTime startTime, 
        @Param("endTime") LocalDateTime endTime);
}
