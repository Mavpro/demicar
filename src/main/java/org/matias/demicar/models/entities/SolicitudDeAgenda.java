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
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name="auto_id")
    private Auto auto;

    @ManyToOne
    @JoinColumn(name="instructor_id")
    private Instructor instructor;

    @OneToOne(mappedBy = "solicitudDeAgenda")
    private Clase clase;

    private String tema;

    private LocalDateTime fechaSolicitud; // Sugerido
    private LocalDateTime fechaClase; // Sugerido

    private String estado; // Sugerido: pendiente, confirmada, cancelada

    private boolean activo;

}
