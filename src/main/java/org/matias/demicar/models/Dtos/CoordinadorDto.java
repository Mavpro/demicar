package org.matias.demicar.models.Dtos;


import lombok.Data;

@Data
public class CoordinadorDto {
    private Long id;
    private String nombre;
    private String email;
    private String telefono;
    private boolean activo;
}
