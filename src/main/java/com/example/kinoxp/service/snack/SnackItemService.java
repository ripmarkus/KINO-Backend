package com.example.kinoxp.service.snack;

import com.example.kinoxp.model.snack.SnackItem;

import java.util.List;
import java.util.Optional;

public interface SnackItemService {
    boolean existsById(Integer id);
    Optional<SnackItem> findById(Integer id);
    List<SnackItem> findAll();
    SnackItem save(SnackItem snackItem);
    void deleteById(Integer id);
}
