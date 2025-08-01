package dev.maniebra.awesomedraw.service;

import dev.maniebra.awesomedraw.model.Painting;
import dev.maniebra.awesomedraw.model.Shape;
import dev.maniebra.awesomedraw.repository.PaintingRepository;
import dev.maniebra.awesomedraw.repository.ShapeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShapeService {

    private final ShapeRepository shapeRepository;
    private final PaintingRepository paintingRepository;
    private final AuthService authService;

    public ShapeService(ShapeRepository shapeRepository, PaintingRepository paintingRepository, AuthService authService) {
        this.shapeRepository = shapeRepository;
        this.paintingRepository = paintingRepository;
        this.authService = authService;
    }

    public List<Shape> findAll() {
        return shapeRepository.findAll();
    }

    public List<Shape> findByPainting(Long paintingId) {
        String username = authService.getCurrentUser().getUsername();
        return shapeRepository.findByPaintingIdAndPaintingUserUsername(paintingId, username);
    }

    public Shape save(Long paintingId, Shape shape) {
        String username = authService.getCurrentUser().getUsername();
        Painting painting = paintingRepository.findByIdAndUserUsername(paintingId, username).orElseThrow();
        shape.setPainting(painting);
        return shapeRepository.save(shape);
    }

    public Shape update(Long id, Shape shape) {
        String username = authService.getCurrentUser().getUsername();
        Shape existing = shapeRepository.findByIdAndPaintingUserUsername(id, username).orElseThrow();
        shape.setId(id);
        shape.setPainting(existing.getPainting());
        return shapeRepository.save(shape);
    }

    public void delete(Long id) {
        String username = authService.getCurrentUser().getUsername();
        Shape shape = shapeRepository.findByIdAndPaintingUserUsername(id, username).orElseThrow();
        shapeRepository.delete(shape);
    }
}