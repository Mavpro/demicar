package org.matias.demicar.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="asignaciones")
@AllArgsConstructor
@NoArgsConstructor
public class Asignacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="coordinador_id")
    private Coordinador coordinador;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "solicitud_id", referencedColumnName = "id")
    private SolicitudDeAgenda solicitudDeAgenda;

    @ManyToOne
    @JoinColumn(name="clase_id")
    private Clase clase;

    private boolean activo;


    // ... otros atributos y métodos
}