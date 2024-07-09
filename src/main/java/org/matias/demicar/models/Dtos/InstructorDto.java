package org.matias.demicar.models.Dtos;


import lombok.Data;


@Data
public class InstructorDto {
    private Long id;
    private String nombre;
    private String email;
    private String telefono;
}