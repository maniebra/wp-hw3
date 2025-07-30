package dev.maniebra.awesomedraw.repositories;

import dev.maniebra.awesomedraw.model.Shape;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShapeRepository extends JpaRepository<Shape, Long> {
}