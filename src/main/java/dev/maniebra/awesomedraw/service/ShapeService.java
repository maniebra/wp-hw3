package dev.maniebra.awesomedraw.service;

import dev.maniebra.awesomedraw.model.Painting;
import dev.maniebra.awesomedraw.model.Shape;
import dev.maniebra.awesomedraw.repositories.PaintingRepository;
import dev.maniebra.awesomedraw.repositories.ShapeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShapeService {

    private final ShapeRepository shapeRepository;
    private final PaintingRepository paintingRepository;

    public ShapeService(ShapeRepository shapeRepository, PaintingRepository paintingRepository) {
        this.shapeRepository = shapeRepository;
        this.paintingRepository = paintingRepository;
    }

    public List<Shape> findAll() {
        return shapeRepository.findAll();
    }

    public List<Shape> findByPainting(Long paintingId) {
        Painting painting = paintingRepository.findById(paintingId).orElseThrow();
        return painting.getShapes();
    }

    public Shape save(Long paintingId, Shape shape) {
        Painting painting = paintingRepository.findById(paintingId).orElseThrow();
        shape.setPainting(painting);
        return shapeRepository.save(shape);
    }

    public Shape update(Long id, Shape shape) {
        shape.setId(id);
        return shapeRepository.save(shape);
    }

    public void delete(Long id) {
        shapeRepository.deleteById(id);
    }
}