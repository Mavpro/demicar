package org.matias.demicar.models.Dtos;


import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ClaseDto {
    private Long id;
    private SolicitudDeAgendaDto solicitudDeAgenda;
    private InstructorDto instructor;
    private String tema;
    private LocalDateTime fechaHora;
    private AutoDto auto;
    private List<AsignacionDto> asignaciones;
    private boolean activo;

}