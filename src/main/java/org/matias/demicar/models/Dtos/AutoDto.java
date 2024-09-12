package org.matias.demicar.models.Dtos;


import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.matias.demicar.models.entities.SolicitudDeAgenda;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AutoDto {
    @Id
    private Long id;

    @NotBlank(message = "La marca es obligatoria")
    @Size(max = 50, message = "La marca no puede tener más de 50 caracteres")
    private String marca;

    @NotBlank(message = "El modelo es obligatorio")
    @Size(max = 50, message = "El modelo no puede tener más de 50 caracteres")
    private String modelo;

    @NotBlank(message = "La matrícula es obligatoria")
    @Pattern(regexp = "^[A-Z]{3}[0-9]{3}[A-Z]$", message = "La matrícula debe tener el formato correcto") // Ajusta el patrón según tu país
    private String matricula;

    @NotNull(message = "El estado es obligatorio")
    private String estado; // Enum EstadoVehiculo


    @NotNull(message = "El campo activo es obligatorio")
    private Boolean activo;

    private List<ClaseDto> clasesAsignadas;

    private List<SolicitudDeAgendaDto> solicitudDeAgenda;
}