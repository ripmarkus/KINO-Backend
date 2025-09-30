package com.example.kinoxp.repository;

import com.example.kinoxp.model.snack.SnackItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SnackItemRepo extends JpaRepository<SnackItem,Integer> {
}
