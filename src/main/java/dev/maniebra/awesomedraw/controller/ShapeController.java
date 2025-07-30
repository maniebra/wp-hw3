package dev.maniebra.awesomedraw.controller;

import dev.maniebra.awesomedraw.model.Shape;
import dev.maniebra.awesomedraw.service.ShapeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shapes")
public class ShapeController {

    private final ShapeService service;

    public ShapeController(ShapeService service) {
        this.service = service;
    }

    @GetMapping
    public List<Shape> all(@RequestParam(required = false) Long paintingId) {
        if (paintingId != null) {
            return service.findByPainting(paintingId);
        }
        return service.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Shape create(@RequestParam Long paintingId, @RequestBody Shape shape) {
        return service.save(paintingId, shape);
    }

    @PutMapping("/{id}")
    public Shape update(@PathVariable Long id, @RequestBody Shape shape) {
        return service.update(id, shape);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}