package org.matias.demicar.services.impl;

import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import org.matias.demicar.exeptions.ResourceNotFoundException;
import org.matias.demicar.models.Dtos.SolicitudDeAgendaDto;
import org.matias.demicar.models.Mappers.AutoMapperService;
import org.matias.demicar.models.Mappers.ClienteMapperService;
import org.matias.demicar.models.Mappers.InstructorMapperService;
import org.matias.demicar.models.Mappers.SolicitudDeAgendaMapperService;
import org.matias.demicar.models.entities.Auto;
import org.matias.demicar.models.entities.Cliente;
import org.matias.demicar.models.entities.Instructor;
import org.matias.demicar.models.entities.SolicitudDeAgenda;
import org.matias.demicar.respositories.AutoRepository;
import org.matias.demicar.respositories.ClienteRepository;
import org.matias.demicar.respositories.InstructorRepository;
import org.matias.demicar.respositories.SolicitudDeAgendaRepository;
import org.matias.demicar.services.SolicitudDeAgendaServiceI;
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
    private final InstructorRepository instructorRepository;
    private final InstructorMapperService instructorMapperService;

    public SolicitudDeAgendaServiceImpl(SolicitudDeAgendaMapperService solicitudDeAgendaMapper, SolicitudDeAgendaRepository solicitudDeAgendaRepository, ClienteMapperService clienteMapperService, ClienteRepository clienteRepository, AutoMapperService autoMapperService, AutoRepository autoRepository, InstructorRepository instructorRepository, InstructorMapperService instructorMapperService) {
        this.solicitudDeAgendaMapper = solicitudDeAgendaMapper;
        this.solicitudDeAgendaRepository = solicitudDeAgendaRepository;
        this.clienteMapperService = clienteMapperService;
        this.clienteRepository = clienteRepository;
        this.autoMapperService = autoMapperService;
        this.autoRepository = autoRepository;
        this.instructorRepository = instructorRepository;
        this.instructorMapperService = instructorMapperService;
    }


    @Override
    public List<SolicitudDeAgendaDto> getSolicitudDeAgendas() {
        List<SolicitudDeAgenda> sol = (List<SolicitudDeAgenda>) solicitudDeAgendaRepository.findAll();
        return sol.stream().map(solicitudDeAgendaMapper::convertToDto).collect(Collectors.toList());
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
    public List<SolicitudDeAgendaDto> obtenerSolicitudDeAgendasPorCliente(Long id) {
        List<SolicitudDeAgenda> solicitudesCliente = solicitudDeAgendaRepository.findByCliente_Id(id);
        return solicitudesCliente.stream().map(solicitudDeAgendaMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<SolicitudDeAgendaDto> obtenerSolicitudDeAgendasPorInstructor(Long id) {
        List<SolicitudDeAgenda> solicitudesCliente = solicitudDeAgendaRepository.findByInstructor_Id(id);
        return solicitudesCliente.stream().map(solicitudDeAgendaMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<SolicitudDeAgendaDto> obtenerSolicitudDeAgendasPorInstructorPorFechas(Long id, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return solicitudDeAgendaRepository.findAllByInstructorIdAndFechaClaseBetween(id,fechaInicio,fechaFin)
                .stream().map(solicitudDeAgendaMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<SolicitudDeAgendaDto> obtenerSolicitudDeAgendasPorClientePorFechas(Long id, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return solicitudDeAgendaRepository.findAllByCliente_IdAndFechaClaseBetween(id,fechaInicio,fechaFin)
                .stream().map(solicitudDeAgendaMapper::convertToDto).collect(Collectors.toList());
    }
    @Override
    public List<SolicitudDeAgendaDto> obtenerSolicitudDeAgendasPorAutoPorFechas(Long id, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return solicitudDeAgendaRepository.findAllByAuto_IdAndFechaClaseBetween(id,fechaInicio,fechaFin)
                .stream().map(solicitudDeAgendaMapper::convertToDto).collect(Collectors.toList());
    }
    @Transactional
    @Override
    public SolicitudDeAgendaDto crearSolicitudDeAgenda(SolicitudDeAgendaDto solicitudDeAgendaDTO) {
        // Obtener el instructor y el cliente a partir de sus IDs
        Instructor instructor = instructorRepository.findById(solicitudDeAgendaDTO.getInstructorId())
                .orElseThrow(() -> new ResourceNotFoundException("Instructor no encontrado"));
        Cliente cliente = clienteRepository.findById(solicitudDeAgendaDTO.getClienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));
        Auto auto = autoRepository.findById(solicitudDeAgendaDTO.getAutoId())
                .orElseThrow(() -> new ResourceNotFoundException("Auto no encontrado"));

        // Verificar que el instructor no tenga clases agendadas en el mismo horario
        LocalDateTime fechaClaseSolicitada = solicitudDeAgendaDTO.getFechaClase();
        List<SolicitudDeAgenda> solicitudesInstructor = solicitudDeAgendaRepository
                .findAllByInstructorIdAndFechaClaseBetween(instructor.getId(), fechaClaseSolicitada.minusMinutes(15), fechaClaseSolicitada.plusMinutes(15));

        if (!solicitudesInstructor.isEmpty()) {
            throw new ValidationException("El instructor ya tiene una clase agendada en este horario o en un intervalo de 15 minutos.");
        }

        // Verificar que el cliente no tenga clases agendadas en el mismo horario
        List<SolicitudDeAgenda> solicitudesCliente = solicitudDeAgendaRepository
                .findAllByCliente_IdAndFechaClaseBetween(cliente.getId(), fechaClaseSolicitada.minusMinutes(15), fechaClaseSolicitada.plusMinutes(15));

        if (!solicitudesCliente.isEmpty()) {
            throw new ValidationException("El cliente ya tiene una clase agendada en este horario o en un intervalo de 15 minutos.");
        }

        // Crear la nueva solicitud de agenda
        SolicitudDeAgenda nuevaSolicitud = solicitudDeAgendaMapper.convertToEntity(solicitudDeAgendaDTO);
        nuevaSolicitud.setInstructor(instructor);
        nuevaSolicitud.setCliente(cliente);
        nuevaSolicitud.setActivo(true); // Marcar la solicitud como activa

        // Guardar la solicitud en la base de datos
        SolicitudDeAgenda solicitudGuardada = solicitudDeAgendaRepository.save(nuevaSolicitud);

        // Retornar el DTO de la solicitud creada
        return solicitudDeAgendaMapper.convertToDto(solicitudGuardada);
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


