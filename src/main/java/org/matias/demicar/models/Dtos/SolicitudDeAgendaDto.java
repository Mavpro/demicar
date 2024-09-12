package org.matias.demicar.models.Dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.matias.demicar.models.entities.Auto;
import org.matias.demicar.models.entities.Instructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudDeAgendaDto {

    private Long id;
    private Long clienteId;  // ID de Cliente
    private Long instructorId;  // ID de Instructor
    private Long autoId;  // ID de Auto
    private Long claseId;  // Si es necesario mantener el DTO completo de Clase
    private LocalDateTime fechaSolicitud;
    private LocalDateTime fechaClase;
    private String tema;
    private String estado;
    private boolean activo;
}