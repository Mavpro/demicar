package org.matias.demicar.models.Dtos;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.matias.demicar.models.entities.Clase;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstructorDto {
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombre;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe ser válido")
    private String email;

    @NotBlank(message = "El teléfono es obligatorio")
    @Pattern(regexp = "\\+?[0-9.-]+", message = "El teléfono debe ser válido")
    private String telefono;
    List<Clase> clasesImpartidas;

    private boolean activo;
}