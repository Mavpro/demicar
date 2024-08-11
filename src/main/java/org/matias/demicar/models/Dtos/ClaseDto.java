package org.matias.demicar.models.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClaseDto {
    private Long id;
    private SolicitudDeAgendaDto solicitudDeAgenda;  // Incluye el DTO completo
    private CoordinadorDto coordinador;  // Incluye el DTO completo
    private LocalDate fechaDeAsignacion;
    private String estado;
    private boolean activo;


    // Otros atributos si son necesarios
}