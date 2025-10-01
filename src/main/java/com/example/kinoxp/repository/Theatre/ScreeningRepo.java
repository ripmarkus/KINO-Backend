package com.example.kinoxp.repository.Theatre;

import com.example.kinoxp.model.theatre.Screening;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScreeningRepo extends JpaRepository<Screening,Integer> {

    public Screening findById(int id);
}
