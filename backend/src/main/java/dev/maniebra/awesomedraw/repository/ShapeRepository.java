package dev.maniebra.awesomedraw.repository;

import dev.maniebra.awesomedraw.model.Shape;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShapeRepository extends JpaRepository<Shape, Long> {
    java.util.List<Shape> findByPaintingUserUsername(String username);
    java.util.List<Shape> findByPaintingIdAndPaintingUserUsername(Long paintingId, String username);
    java.util.Optional<Shape> findByIdAndPaintingUserUsername(Long id, String username);
}