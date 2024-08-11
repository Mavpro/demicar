package org.matias.demicar.services;

import org.matias.demicar.models.Dtos.ClienteDto;
import org.matias.demicar.models.Dtos.SolicitudDeAgendaDto;

import java.util.List;
import java.util.Optional;

public interface SolicitudDeAgendaServiceI {
List<SolicitudDeAgendaDto> getSolicitudDeAgendas();
    Optional<SolicitudDeAgendaDto> obtenerSolicitudDeAgendaPorId(Long id);

    List<SolicitudDeAgendaDto> obtenerSolicitudesPorCliente(ClienteDto cliente);

    SolicitudDeAgendaDto crearSolicitudDeAgenda(SolicitudDeAgendaDto solicitudDeAgendaDTO);

    SolicitudDeAgendaDto actualizarSolicitudDeAgenda(Long id, SolicitudDeAgendaDto solicitudDeAgendaDTO);

    Optional eliminarSolicitudDeAgenda(Long id);

    //Boolean existByNombreyApellido(String nombre);

    //Boolean existByCorreo(String correo);

    Boolean existById(Long id);

}
