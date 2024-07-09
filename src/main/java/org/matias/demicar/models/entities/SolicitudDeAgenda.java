package org.matias.demicar.models.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class SolicitudDeAgenda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Cliente cliente;

    private String estado; // Sugerido: pendiente, confirmada, cancelada
    private LocalDateTime fechaSolicitud; // Sugerido
    private LocalDateTime fechaClase; // Sugerido
}
