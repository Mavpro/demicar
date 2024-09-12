package org.matias.demicar.models.Dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClaseDto {
    private Long id;
    private SolicitudDeAgendaDto solicitudDeAgenda;  // Incluye el DTO completo
    private CoordinadorDto coordinador;  // Incluye el DTO completo
    private LocalDateTime fechaDeAsignacion;
    private String estado;
    @NotBlank(message = "El tema es obligatorio")
    @Size(min = 5, max = 255, message = "El tema debe tener entre 5 y 255 caracteres")
    private String tema;

    @Min(value = 1, message = "La evaluación debe ser mayor o igual a 1")
    @Max(value = 5, message = "La evaluación debe ser menor o igual a 5")
    private int eval;
    private boolean activo;



    // Otros atributos si son necesarios
}