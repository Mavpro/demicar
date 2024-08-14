package org.matias.demicar.services.impl;

import org.matias.demicar.models.Dtos.ClienteDto;
import org.matias.demicar.models.Dtos.InstructorDto;
import org.matias.demicar.models.Dtos.SolicitudDeAgendaDto;
import org.matias.demicar.models.Mappers.AutoMapperService;
import org.matias.demicar.models.Mappers.ClienteMapperService;
import org.matias.demicar.models.Mappers.SolicitudDeAgendaMapperService;
import org.matias.demicar.models.entities.Cliente;
import org.matias.demicar.models.entities.SolicitudDeAgenda;
import org.matias.demicar.respositories.AutoRepository;
import org.matias.demicar.respositories.ClienteRepository;
import org.matias.demicar.respositories.SolicitudDeAgendaRepository;
import org.matias.demicar.services.SolicitudDeAgendaServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SolicitudDeAgendaServiceImpl implements SolicitudDeAgendaServiceI {


    private final SolicitudDeAgendaMapperService solicitudDeAgendaMapper;
    private final SolicitudDeAgendaRepository solicitudDeAgendaRepository;
    private final ClienteMapperService clienteMapperService;
    private final ClienteRepository clienteRepository;
    private final AutoMapperService autoMapperService;
    private final AutoRepository autoRepository;

    public SolicitudDeAgendaServiceImpl(SolicitudDeAgendaMapperService solicitudDeAgendaMapper, SolicitudDeAgendaRepository solicitudDeAgendaRepository, ClienteMapperService clienteMapperService, ClienteRepository clienteRepository, AutoMapperService autoMapperService, AutoRepository autoRepository) {
        this.solicitudDeAgendaMapper = solicitudDeAgendaMapper;
        this.solicitudDeAgendaRepository = solicitudDeAgendaRepository;
        this.clienteMapperService = clienteMapperService;
        this.clienteRepository = clienteRepository;
        this.autoMapperService = autoMapperService;
        this.autoRepository = autoRepository;
    }


    @Override
    public List<SolicitudDeAgendaDto> getSolicitudDeAgendas() {
        return List.of();
    }

    @Override
    public Optional<SolicitudDeAgendaDto> obtenerSolicitudDeAgendaPorId(Long id) {
        Optional<SolicitudDeAgenda> solicitudDeAgenda = solicitudDeAgendaRepository.findById(id);
        if (solicitudDeAgenda.isPresent()) {
            return solicitudDeAgenda.map(solicitudDeAgendaMapper::convertToDto);
        }
        return Optional.empty();
    }

    @Override
    public List<SolicitudDeAgendaDto> obtenerSolicitudDeAgendasPorCliente(ClienteDto cliente, Long id) {
        return List.of();
    }

    @Override
    public List<SolicitudDeAgendaDto> obtenerSolicitudDeAgendasPorInstructor(InstructorDto instructor, Long id) {
        return List.of();
    }

    @Override
    public List<SolicitudDeAgendaDto> obtenerSolicitudDeAgendasPorInstructorPorFechas(InstructorDto instructor, Long id, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return List.of();
    }

    @Override
    public List<SolicitudDeAgendaDto> obtenerSolicitudDeAgendasPorClientePorFechas(ClienteDto cliente, Long id, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return List.of();
    }

    @Override
    public SolicitudDeAgendaDto crearSolicitudDeAgenda(SolicitudDeAgendaDto solicitudDeAgendaDTO) {
        return null;
    }

    @Override
    public SolicitudDeAgendaDto actualizarSolicitudDeAgenda(SolicitudDeAgendaDto solicitudDeAgendaDTO, Long id) {
        return null;
    }

    @Override
    public Optional eliminarSolicitudDeAgenda(Long id) {
        return Optional.empty();
    }

    @Override
    public Boolean existConflictoDeHorario(SolicitudDeAgendaDto solicitudDeAgendaDTO) {
        return null;
    }

    @Override
    public Boolean existById(Long id) {
        return null;
    }
}


