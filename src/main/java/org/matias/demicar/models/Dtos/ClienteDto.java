package org.matias.demicar.models.Dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.Check;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDto {

    private Long id;

    @NotBlank(message = "El nombre y apellido son obligatorios")
    @Size(min = 2, max = 100, message = "El nombre y apellido deben tener entre 2 y 100 caracteres")
    private String nombreApellido;

    @NotBlank(message = "El DNI es obligatorio")
    @Pattern(regexp = "\\d{7,8}", message = "El DNI debe ser un número de 7 u 8 dígitos")
    private String dni;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe ser válido")
    private String email;

    @NotBlank(message = "El teléfono es obligatorio")
    @Pattern(regexp = "\\d{10}", message = "El teléfono debe tener 10 dígitos")
    private String telefono;

    @NotNull(message = "El estado de 'activo' no puede ser nulo")
    private Boolean activo;

    // Incluye la lista de solicitudes si es necesario, pero maneja las referencias cíclicas
    private List<SolicitudDeAgendaDto> solicitudesDeAgenda;

}
