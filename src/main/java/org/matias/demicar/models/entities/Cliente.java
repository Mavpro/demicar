package org.matias.demicar.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String apellido;
    private String dni;
    private String email; // Sugerido
    private String telefono; // Sugerido

    @OneToMany(mappedBy = "cliente")
    private List<SolicitudDeAgenda> solicitudesDeAgenda;
}