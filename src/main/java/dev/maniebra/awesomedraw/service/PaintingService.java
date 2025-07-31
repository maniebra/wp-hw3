package dev.maniebra.awesomedraw.service;

import dev.maniebra.awesomedraw.model.Painting;
import dev.maniebra.awesomedraw.repository.PaintingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaintingService {

    private final PaintingRepository repository;

    public PaintingService(PaintingRepository repository) {
        this.repository = repository;
    }

    public List<Painting> findAll() {
        return repository.findAll();
    }

    public Painting findById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    public Painting save(Painting painting) {
        return repository.save(painting);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}