package org.matias.demicar.services;

import org.matias.demicar.models.Dtos.CoordinadorDto;

import java.util.List;
import java.util.Optional;

public interface CoordinadorServiceI {
List<CoordinadorDto> getCoordinadors();
    Optional<CoordinadorDto> obtenerCoordinadorPorId(Long id);

    List<CoordinadorDto> obtenerCoordinadorPorNombre(String nombre);

    CoordinadorDto crearCoordinador(CoordinadorDto coordinadorDTO);

    CoordinadorDto actualizarCoordinador(Long id, CoordinadorDto coordinadorDTO);

    Optional eliminarCoordinador(Long id);

    Boolean existByNombreyApellido(String nombre);

    Boolean existByCorreo(String correo);

    Boolean existById(Long id);

}
