package org.matias.demicar.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name = "coordinadores")
@AllArgsConstructor
@NoArgsConstructor
public class Coordinador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String email; // Sugerido
    private String telefono; // Sugerido
    private boolean activo;

    @OneToMany(mappedBy="coordinador")
    private List<Clase> clases;
}