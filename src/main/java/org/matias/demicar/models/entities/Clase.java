package org.matias.demicar.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.matias.demicar.models.Dtos.SolicitudDeAgendaDto;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "clases")
@AllArgsConstructor
@NoArgsConstructor
public class Clase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private SolicitudDeAgenda solicitudDeAgenda;

    @ManyToOne
    @JoinColumn(name = "instructor_id")
    private Instructor instructor;

    private String tema; // Sugerido
    private LocalDateTime fechaHora; // Sugerido

    @ManyToOne
    @JoinColumn(name = "auto_id")
    private Auto auto;

    @OneToMany(mappedBy="clase")
    private List<Asignacion> asignaciones;

}