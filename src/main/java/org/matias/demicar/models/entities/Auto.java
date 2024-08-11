package org.matias.demicar.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Table(name = "autos")
@AllArgsConstructor
@NoArgsConstructor
public class Auto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String marca; // Sugerido: Toyota, Ford, etc.
    private String modelo; // Sugerido: Corolla, Fiesta, etc.
    private String matricula; // Sugerido: Matrícula del vehículo
    private String estado; // Sugerido: disponible, en mantenimiento, etc.

    @OneToMany(mappedBy = "auto", cascade = CascadeType.ALL)
    private List<SolicitudDeAgenda> solicitudDeAgenda;

    private boolean activo;

}
