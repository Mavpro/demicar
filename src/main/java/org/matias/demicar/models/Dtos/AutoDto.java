package org.matias.demicar.models.Dtos;


import lombok.Data;


@Data
public class AutoDto {
    private Long id;
    private String marca;
    private String modelo;
    private String matricula;
    private String estado;
}
