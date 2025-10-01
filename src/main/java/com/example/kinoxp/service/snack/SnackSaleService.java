package com.example.kinoxp.service.snack;

import com.example.kinoxp.model.snack.SnackSale;

import java.util.List;
import java.util.Optional;

public interface SnackSaleService {
    boolean existsById(Integer id);
    Optional<SnackSale> findById(Integer id);
    List<SnackSale> findAll();
    SnackSale save(SnackSale snackSale);
    void deleteById(Integer id);
}
