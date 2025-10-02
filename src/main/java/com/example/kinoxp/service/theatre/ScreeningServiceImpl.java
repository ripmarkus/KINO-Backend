package com.example.kinoxp.service.theatre;

import com.example.kinoxp.model.theatre.Screening;
import com.example.kinoxp.repository.Theatre.ScreeningRepo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ScreeningServiceImpl implements ScreeningService {
    
    private final ScreeningRepo screeningRepo;
    
    public ScreeningServiceImpl(ScreeningRepo screeningRepo) {
        this.screeningRepo = screeningRepo;
    }

    public Screening getRequiredScreening(Integer id) {
        return screeningRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Screening not found"));
    }

    @Override
    public boolean existsById(Integer id) {
        return screeningRepo.existsById(id);
    }
    
    @Override
    public Optional<Screening> findById(Integer id) {
        return screeningRepo.findById(id);
    }
    
    @Override
    public List<Screening> findAll() {
        return screeningRepo.findAll();
    }
    
    @Override
    public Screening save(Screening screening) {
        return screeningRepo.save(screening);
    }
    
    @Override
    public void deleteById(Integer id) {
        screeningRepo.deleteById(id);
    }
}
