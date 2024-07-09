package org.matias.demicar.models.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Instructor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String email; // Sugerido
    private String telefono; // Sugerido

    @OneToMany(mappedBy = "instructor")
    List<Clase> clasesImpartidas;
}