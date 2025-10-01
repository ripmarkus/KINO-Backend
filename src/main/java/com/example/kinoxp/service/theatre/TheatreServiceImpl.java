package com.example.kinoxp.service.theatre;

import com.example.kinoxp.model.theatre.Theatre;
import com.example.kinoxp.repository.TheatreRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TheatreServiceImpl implements TheatreService {
    
    private final TheatreRepo theatreRepo;
    
    public TheatreServiceImpl(TheatreRepo theatreRepo) {
        this.theatreRepo = theatreRepo;
    }

    @Override
    public boolean existsById(Integer id) {
        return theatreRepo.existsById(id);
    }
    
    @Override
    public Optional<Theatre> findById(Integer id) {
        return theatreRepo.findById(id);
    }

    @Override
    public List<Theatre> findAll() {
        return theatreRepo.findAll();
    }

    @Override
    public Theatre save(Theatre theatre) {
        return theatreRepo.save(theatre);
    }

    @Override
    public void deleteById(Integer id) {
        theatreRepo.deleteById(id);
    }
}
