package dev.maniebra.awesomedraw.dto;

import dev.maniebra.awesomedraw.model.types.ShapeType;
import lombok.Data;

@Data
public class ShapeRequestDto {

    private ShapeType type;
    private double x;
    private double y;
    private double size;
    /**
     * Identifier of the painting this shape belongs to. This value is optional
     * for update operations but required on creation.
     */
    private Long paintingId;
}