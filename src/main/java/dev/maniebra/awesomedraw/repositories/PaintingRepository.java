package dev.maniebra.awesomedraw.repositories;

import dev.maniebra.awesomedraw.model.Painting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaintingRepository extends JpaRepository<Painting, Long> {
}