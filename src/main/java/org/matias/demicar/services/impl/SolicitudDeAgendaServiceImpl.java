package org.matias.demicar.services.impl;

import org.matias.demicar.models.Dtos.ClienteDto;
import org.matias.demicar.models.Dtos.SolicitudDeAgendaDto;
import org.matias.demicar.models.Mappers.ClienteMapperService;
import org.matias.demicar.models.Mappers.SolicitudDeAgendaMapperService;
import org.matias.demicar.models.entities.Cliente;
import org.matias.demicar.models.entities.SolicitudDeAgenda;
import org.matias.demicar.respositories.SolicitudDeAgendaRepository;
import org.matias.demicar.services.SolicitudDeAgendaServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SolicitudDeAgendaServiceImpl implements SolicitudDeAgendaServiceI {

    @Autowired
    private final SolicitudDeAgendaMapperService solicitudDeAgendaMapper;
    private final SolicitudDeAgendaRepository solicitudDeAgendaRepository;
    @Autowired
    private ClienteMapperService clienteMapperService;

    public SolicitudDeAgendaServiceImpl(SolicitudDeAgendaMapperService solicitudDeAgendaMapper, SolicitudDeAgendaRepository solicitudDeAgendaRepository) {
        this.solicitudDeAgendaMapper = solicitudDeAgendaMapper;
        this.solicitudDeAgendaRepository = solicitudDeAgendaRepository;
    }


    @Override
    public List<SolicitudDeAgendaDto> getSolicitudDeAgendas() {
        List<SolicitudDeAgenda> solicitudDeAgendas = (List<SolicitudDeAgenda>) solicitudDeAgendaRepository.findAll();
        return solicitudDeAgendas.stream()
                .map(solicitudDeAgendaMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<SolicitudDeAgendaDto> obtenerSolicitudDeAgendaPorId(Long id) {
Optional<SolicitudDeAgenda> solicitudDeAgenda = solicitudDeAgendaRepository.findById(id);
    return solicitudDeAgenda.map(solicitudDeAgendaMapper::convertToDto);
    }

    @Override
    public List<SolicitudDeAgendaDto> obtenerSolicitudesPorCliente(ClienteDto cliente) {
        Cliente entity = clienteMapperService.convertToEntity(cliente);
        List<SolicitudDeAgenda> solicitudDeAgendas = (List<SolicitudDeAgenda>) solicitudDeAgendaRepository.findByCliente(entity);
        return  solicitudDeAgendas.stream().filter(x->x.isActivo()).map(solicitudDeAgendaMapper::convertToDto).collect(Collectors.toList());


    }


    @Override
    public SolicitudDeAgendaDto crearSolicitudDeAgenda(SolicitudDeAgendaDto solicitudDeAgendaDTO) {
        System.out.println(solicitudDeAgendaDTO);
        solicitudDeAgendaDTO.setActivo(true);
       SolicitudDeAgenda solicitudDeAgenda =  solicitudDeAgendaRepository.save(solicitudDeAgendaMapper.convertToEntity(solicitudDeAgendaDTO));
       return solicitudDeAgendaMapper.convertToDto(solicitudDeAgenda);
    }

    @Override
    public SolicitudDeAgendaDto actualizarSolicitudDeAgenda(Long id, SolicitudDeAgendaDto solicitudDeAgendaDTO) {

return null;

    }

    @Override
    public Optional eliminarSolicitudDeAgenda(Long id) {
        SolicitudDeAgenda solicitudDeAgenda = solicitudDeAgendaRepository.findById(id).orElse(null);
        solicitudDeAgenda.setActivo(false);
        solicitudDeAgendaRepository.save(solicitudDeAgenda);
        return Optional.of(solicitudDeAgenda);
    }

  /*  @Override
    public Boolean existByNombreyApellido(String nombre_y_apellido){
        return solicitudDeAgendaRepository.existsByNombre(nombre_y_apellido);
    }

    @Override
    public Boolean existByCorreo(String correo) {
        return solicitudDeAgendaRepository.existsByEmail(correo);
    }*/

    @Override
    public Boolean existById(Long id) {
        return solicitudDeAgendaRepository.existsById(id);
    }


}


