package org.matias.demicar.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name="clases")
@AllArgsConstructor
@NoArgsConstructor
public class Clase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "solicitudAsignada")
    private SolicitudDeAgenda solicitudDeAgenda;

    @ManyToOne
    @JoinColumn(name="coordinador_id")
    private Coordinador coordinador;

    private LocalDateTime fechaDeAsignacion;

    private String tema;

    private int eval;

    private String estado;

    private boolean activo;


    // ... otros atributos y métodos
}