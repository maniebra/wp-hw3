package dev.maniebra.awesomedraw.model;
import dev.maniebra.awesomedraw.model.types.ShapeType;
import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
public class Shape {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ShapeType type;

    private double x;
    private double y;
    private double size;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "painting_id")
    private Painting painting;
}