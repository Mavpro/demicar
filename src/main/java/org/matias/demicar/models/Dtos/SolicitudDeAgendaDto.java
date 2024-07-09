package org.matias.demicar.models.Dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SolicitudDeAgendaDto {
    private Long id;
    private ClienteDto cliente;
    private String estado;
    private LocalDateTime fechaSolicitud;
    private LocalDateTime fechaClase;
}
