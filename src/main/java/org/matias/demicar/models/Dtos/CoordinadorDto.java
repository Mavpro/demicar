package org.matias.demicar.models.Dtos;


import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;
import org.matias.demicar.models.entities.Clase;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoordinadorDto {
    private Long id;
    private String nombre;
    private String email;
    private String telefono;
    private boolean activo;
    // Incluye la lista de clases solo si es necesario y se deben manejar las referencias cíclicas con cuidado
    private List<ClaseDto> clases;
}
