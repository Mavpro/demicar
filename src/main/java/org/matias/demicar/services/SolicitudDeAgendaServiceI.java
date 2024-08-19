package org.matias.demicar.services;

import org.matias.demicar.models.Dtos.ClienteDto;
import org.matias.demicar.models.Dtos.InstructorDto;
import org.matias.demicar.models.Dtos.SolicitudDeAgendaDto;
import org.matias.demicar.models.entities.Instructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SolicitudDeAgendaServiceI {
List<SolicitudDeAgendaDto> getSolicitudDeAgendas();
    Optional<SolicitudDeAgendaDto> obtenerSolicitudDeAgendaPorId(Long id);

    List<SolicitudDeAgendaDto>  obtenerSolicitudDeAgendasPorCliente(Long id);

    List<SolicitudDeAgendaDto> obtenerSolicitudDeAgendasPorInstructor(Long id);

    List<SolicitudDeAgendaDto> obtenerSolicitudDeAgendasPorInstructorPorFechas(Long id, LocalDateTime fechaInicio, LocalDateTime fechaFin);
    List<SolicitudDeAgendaDto> obtenerSolicitudDeAgendasPorClientePorFechas(Long id, LocalDateTime fechaInicio, LocalDateTime fechaFin);


    SolicitudDeAgendaDto crearSolicitudDeAgenda(SolicitudDeAgendaDto solicitudDeAgendaDTO);

    SolicitudDeAgendaDto actualizarSolicitudDeAgenda(SolicitudDeAgendaDto solicitudDeAgendaDTO,Long id);

    Optional eliminarSolicitudDeAgenda(Long id);


    Boolean existConflictoDeHorario(SolicitudDeAgendaDto solicitudDeAgendaDTO);

    Boolean existById(Long id);

}
