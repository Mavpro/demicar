package org.matias.demicar.models.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Clase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Instructor instructor;

    private String tema; // Sugerido
    private LocalDateTime fechaHora; // Sugerido

    @ManyToOne
    private Auto auto;
}