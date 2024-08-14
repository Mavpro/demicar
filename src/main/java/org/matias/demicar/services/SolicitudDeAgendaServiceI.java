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

    List<SolicitudDeAgendaDto>  obtenerSolicitudDeAgendasPorCliente(ClienteDto cliente, Long id);

    List<SolicitudDeAgendaDto> obtenerSolicitudDeAgendasPorInstructor(InstructorDto instructor,Long id);

    List<SolicitudDeAgendaDto> obtenerSolicitudDeAgendasPorInstructorPorFechas(InstructorDto instructor, Long id, LocalDateTime fechaInicio, LocalDateTime fechaFin);
    List<SolicitudDeAgendaDto> obtenerSolicitudDeAgendasPorClientePorFechas(ClienteDto cliente, Long id, LocalDateTime fechaInicio, LocalDateTime fechaFin);


    SolicitudDeAgendaDto crearSolicitudDeAgenda(SolicitudDeAgendaDto solicitudDeAgendaDTO);

    SolicitudDeAgendaDto actualizarSolicitudDeAgenda(SolicitudDeAgendaDto solicitudDeAgendaDTO,Long id);

    Optional eliminarSolicitudDeAgenda(Long id);


    Boolean existConflictoDeHorario(SolicitudDeAgendaDto solicitudDeAgendaDTO);

    Boolean existById(Long id);

}
