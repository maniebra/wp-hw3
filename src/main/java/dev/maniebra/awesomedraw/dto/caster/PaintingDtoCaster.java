package dev.maniebra.awesomedraw.dto.caster;

import dev.maniebra.awesomedraw.dto.PaintingDto;
import dev.maniebra.awesomedraw.model.Painting;

public class PaintingDtoCaster {
    public static PaintingDto toDto(Painting painting) {
        PaintingDto dto = new PaintingDto();
        dto.setId(painting.getId());
        dto.setName(painting.getName());
        return dto;
    }
}
