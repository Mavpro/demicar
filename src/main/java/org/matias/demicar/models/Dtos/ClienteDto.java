package org.matias.demicar.models.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDto {
    private Long id;
    private String nombreApellido;
    private String dni;
    private String email;
    private String telefono;
}