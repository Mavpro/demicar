package org.matias.demicar.models.Dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.Check;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDto {

    private Long id;

    @NotBlank(message = "El nombre y apellido no puede estar vacío")
    @Size(min = 2, max = 60, message = "El nombre debe tener entre 2 y 30 caracteres")
    private String nombreApellido;

    @NotBlank(message = "El número de DNI no puede estar vacío")
    @Pattern(regexp = "[0-9]{8}", message = "El DNI debe tener 8 dígitos numéricos")
    private String dni;

    @NotBlank(message = "El correo electrónico no puede estar vacío")
    @Email(message = "El correo electrónico debe ser válido")
    private String email;

    @NotBlank(message = "El número de teléfono no puede estar vacío")
    @Pattern(regexp = "[0-9]{10}", message = "El número de teléfono debe tener al menos 9 dígitos numéricos")
    private String telefono;
    @JsonManagedReference
    private List<SolicitudDeAgendaDto> solicitudesDeAgenda;


    private boolean activo;


    // Getters and setters omitted for brevity
}
