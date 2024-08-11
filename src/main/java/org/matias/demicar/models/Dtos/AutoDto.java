package org.matias.demicar.models.Dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AutoDto {
    private Long id;
    private String marca;
    private String modelo;
    private String matricula;
    private String estado;
    private List<ClaseDto> clasesAsignadas;
    private boolean activo;

}
