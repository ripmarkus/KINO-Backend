package com.example.kinoxp.service.snack;

import com.example.kinoxp.model.snack.SnackSale;
import com.example.kinoxp.repository.Snack.SnackSaleRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SnackSaleServiceImpl implements SnackSaleService {
    
    private final SnackSaleRepo snackSaleRepo;
    
    public SnackSaleServiceImpl(SnackSaleRepo snackSaleRepo) {
        this.snackSaleRepo = snackSaleRepo;
    }

    @Override
    public boolean existsById(Integer id) {
        return snackSaleRepo.existsById(id);
    }
    
    @Override
    public Optional<SnackSale> findById(Integer id) {
        return snackSaleRepo.findById(id);
    }
    
    @Override
    public List<SnackSale> findAll() {
        return snackSaleRepo.findAll();
    }
    
    @Override
    public SnackSale save(SnackSale snackSale) {
        return snackSaleRepo.save(snackSale);
    }
    
    @Override
    public void deleteById(Integer id) {
        snackSaleRepo.deleteById(id);
    }
}
