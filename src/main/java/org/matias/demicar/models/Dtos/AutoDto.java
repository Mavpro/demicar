package org.matias.demicar.models.Dtos;


import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AutoDto {
    @Id
    private Long id;
    private String marca;
    private String modelo;
    private String matricula;
    private String estado;
    private boolean activo;

    // Incluye la lista de clases asignadas si es necesario, pero maneja las referencias cíclicas
    private List<ClaseDto> clasesAsignadas;
}