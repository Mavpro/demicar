package org.matias.demicar.models.Dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.matias.demicar.models.entities.Instructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudDeAgendaDto {

    private Long id;
    private ClienteDto cliente;  // DTO para Cliente
    private InstructorDto instructor;  // DTO para Instructor
    private ClaseDto clase;  // DTO para Clase
    private LocalDateTime fechaSolicitud;
    private LocalDateTime fechaClase;
    private String tema;
    private String estado;
    private boolean activo;
}