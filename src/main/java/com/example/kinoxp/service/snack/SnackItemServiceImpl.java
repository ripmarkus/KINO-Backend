package com.example.kinoxp.service.snack;

import com.example.kinoxp.exception.EntityNotFoundException;
import com.example.kinoxp.model.snack.SnackItem;
import com.example.kinoxp.repository.Snack.SnackItemRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SnackItemServiceImpl implements SnackItemService {
    
    private final SnackItemRepo snackItemRepo;
    
    public SnackItemServiceImpl(SnackItemRepo snackItemRepo) {
        this.snackItemRepo = snackItemRepo;
    }
    
    public SnackItem getRequiredSnackItem(Integer id) {
        return snackItemRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SnackItem", id));
    }

    @Override
    public boolean existsById(Integer id) {
        return snackItemRepo.existsById(id);
    }
    
    @Override
    public Optional<SnackItem> findById(Integer id) {
        return snackItemRepo.findById(id);
    }
    
    @Override
    public List<SnackItem> findAll() {
        return snackItemRepo.findAll();
    }
    
    @Override
    public SnackItem save(SnackItem snackItem) {
        return snackItemRepo.save(snackItem);
    }
    
    @Override
    public void deleteById(Integer id) {
        snackItemRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SnackItem", id));
        snackItemRepo.deleteById(id);
    }
}
