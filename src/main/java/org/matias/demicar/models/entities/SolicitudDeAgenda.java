package org.matias.demicar.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name="solicitudes_de_agenda")
@AllArgsConstructor
@NoArgsConstructor
public class SolicitudDeAgenda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
@JoinColumn(name="cliente_id")
    @JsonBackReference

    private Cliente cliente;

private String estado; // Sugerido: pendiente, confirmada, cancelada
private LocalDateTime fechaSolicitud; // Sugerido
private LocalDateTime fechaClase; // Sugerido
private boolean activo;

}
