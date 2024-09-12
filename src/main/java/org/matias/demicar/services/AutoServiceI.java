package org.matias.demicar.services;

import org.matias.demicar.models.Dtos.AutoDto;
import org.matias.demicar.models.Dtos.ClienteDto;
import org.matias.demicar.models.Dtos.SolicitudDeAgendaDto;

import java.util.List;
import java.util.Optional;

public interface AutoServiceI {
List<AutoDto> getAutos();
    Optional<AutoDto> obtenerAutoPorId(Long id);

    AutoDto crearAuto(AutoDto autoDTO);

    List<AutoDto> obtenerAutosPorMatricula(String matricula);

    AutoDto actualizarAuto(Long id, AutoDto autoDTO);

    Optional eliminarAuto(Long id);

    Boolean existByMatricula(String matricula);

    Boolean existById(Long id);

}
