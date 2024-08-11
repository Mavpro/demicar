package org.matias.demicar.services;

import org.matias.demicar.models.Dtos.InstructorDto;

import java.util.List;
import java.util.Optional;

public interface InstructorServiceI {
List<InstructorDto> getInstructors();
    Optional<InstructorDto> obtenerInstructorPorId(Long id);

    List<InstructorDto> obtenerInstructorPorNombre(String nombre);

    InstructorDto crearInstructor(InstructorDto instructorDTO);

    InstructorDto actualizarInstructor(Long id, InstructorDto instructorDTO);

    Optional eliminarInstructor(Long id);

    Boolean existByNombreyApellido(String nombre);

    Boolean existByCorreo(String correo);

    Boolean existById(Long id);

}
