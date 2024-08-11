package org.matias.demicar.models.Dtos;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.matias.demicar.models.entities.Clase;
import org.matias.demicar.models.entities.Instructor;
import org.matias.demicar.models.entities.SolicitudDeAgenda;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstructorDto {
    private Long id;
    private String nombre;
    private String email;
    private String telefono;
    private boolean activo;


    private List<SolicitudDeAgenda> solicitudesDeAgenda;
}