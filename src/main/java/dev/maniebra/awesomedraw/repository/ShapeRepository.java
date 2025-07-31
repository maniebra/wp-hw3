package dev.maniebra.awesomedraw.repository;

import dev.maniebra.awesomedraw.model.Shape;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShapeRepository extends JpaRepository<Shape, Long> {
}