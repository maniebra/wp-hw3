package dev.maniebra.awesomedraw.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Painting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "painting", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Shape> shapes = new ArrayList<>();
}