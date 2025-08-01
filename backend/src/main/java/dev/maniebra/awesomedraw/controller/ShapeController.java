package dev.maniebra.awesomedraw.controller;

import dev.maniebra.awesomedraw.dto.ShapeDto;
import dev.maniebra.awesomedraw.dto.ShapeRequestDto;
import dev.maniebra.awesomedraw.dto.caster.ShapeDtoCaster;
import dev.maniebra.awesomedraw.model.Shape;
import dev.maniebra.awesomedraw.service.ShapeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static dev.maniebra.awesomedraw.dto.caster.ShapeDtoCaster.fromDto;
import static dev.maniebra.awesomedraw.dto.caster.ShapeDtoCaster.toDto;

@RestController
@RequestMapping("/api/shapes")
public class ShapeController {

    private final ShapeService service;

    public ShapeController(ShapeService service) {
        this.service = service;
    }

    @GetMapping
    public List<ShapeDto> all(@RequestParam(required = false) Long paintingId) {
        List<Shape> shapes = paintingId != null
                ? service.findByPainting(paintingId)
                : service.findAll();
        return shapes.stream().map(ShapeDtoCaster::toDto).collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Shape create(@RequestBody ShapeRequestDto dto) {
        return service.save(dto.getPaintingId(), fromDto(dto));
    }

    @PutMapping("/{id}")
    public ShapeDto update(@PathVariable Long id, @RequestBody ShapeRequestDto dto) {
        Shape shape = fromDto(dto);
        Shape updated = service.update(id, shape);
        return toDto(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}