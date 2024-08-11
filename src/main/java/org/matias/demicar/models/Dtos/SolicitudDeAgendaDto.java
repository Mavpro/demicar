package org.matias.demicar.models.Dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SolicitudDeAgendaDto {
    private Long id;
    @JsonBackReference
    private ClienteDto cliente;
    private String estado;
    private LocalDateTime fechaSolicitud;
    private LocalDateTime fechaClase;
    private boolean activo;

}