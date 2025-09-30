package com.example.kinoxp.repository;

import com.example.kinoxp.model.snack.SnackItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TheatreRepo extends JpaRepository<SnackItem,Integer> {
}
