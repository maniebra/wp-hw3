package dev.maniebra.awesomedraw.dto.caster;

import dev.maniebra.awesomedraw.dto.ShapeDto;
import dev.maniebra.awesomedraw.dto.ShapeRequestDto;
import dev.maniebra.awesomedraw.model.Shape;

public class ShapeDtoCaster {
    public static ShapeDto toDto(Shape shape) {
        ShapeDto dto = new ShapeDto();
        dto.setId(shape.getId());
        dto.setType(shape.getType());
        dto.setX(shape.getX());
        dto.setY(shape.getY());
        dto.setSize(shape.getSize());
        if (shape.getPainting() != null) {
            dto.setPaintingId(shape.getPainting().getId());
        }
        return dto;
    }

    public static Shape fromDto(ShapeRequestDto dto) {
        Shape shape = new Shape();
        shape.setType(dto.getType());
        shape.setX(dto.getX());
        shape.setY(dto.getY());
        shape.setSize(dto.getSize());
        return shape;
    }
}
