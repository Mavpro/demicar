package org.matias.demicar.models.Dtos;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ClaseDto {
    private Long id;
    private InstructorDto instructor;
    private String tema;
    private LocalDateTime fechaHora;
    private AutoDto auto;
}