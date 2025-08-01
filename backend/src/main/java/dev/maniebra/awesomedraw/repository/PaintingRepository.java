package dev.maniebra.awesomedraw.repository;

import dev.maniebra.awesomedraw.model.Painting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaintingRepository extends JpaRepository<Painting, Long> {
    java.util.List<Painting> findByUserUsername(String username);
    java.util.Optional<Painting> findByIdAndUserUsername(Long id, String username);
}