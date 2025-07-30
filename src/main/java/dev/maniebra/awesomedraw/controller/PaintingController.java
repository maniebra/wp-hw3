package dev.maniebra.awesomedraw.controller;

import dev.maniebra.awesomedraw.model.Painting;
import dev.maniebra.awesomedraw.service.PaintingService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/paintings")
public class PaintingController {

    private final PaintingService service;

    public PaintingController(PaintingService service) {
        this.service = service;
    }

    @GetMapping
    public List<Painting> all() {
        return service.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Painting create(@RequestBody Painting painting) {
        return service.save(painting);
    }

    @GetMapping("/{id}")
    public Painting one(@PathVariable Long id) {
        return service.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}