package org.matias.demicar.models.Dtos;


import lombok.Data;
import org.matias.demicar.models.entities.Asignacion;

import java.util.List;

@Data
public class CoordinadorDto {
    private Long id;
    private String nombre;
    private String email;
    private String telefono;
    private List<Asignacion> asignaciones;
    private boolean activo;
}
