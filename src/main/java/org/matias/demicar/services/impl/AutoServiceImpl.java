package org.matias.demicar.services.impl;

import jakarta.transaction.Transactional;
import org.matias.demicar.models.Dtos.AutoDto;

import org.matias.demicar.models.Dtos.ClienteDto;
import org.matias.demicar.models.Dtos.SolicitudDeAgendaDto;
import org.matias.demicar.models.Mappers.AutoMapperService;
import org.matias.demicar.models.Mappers.SolicitudDeAgendaMapperService;
import org.matias.demicar.models.entities.Auto;
import org.matias.demicar.models.entities.Cliente;
import org.matias.demicar.models.entities.SolicitudDeAgenda;
import org.matias.demicar.respositories.AutoRepository;
import org.matias.demicar.respositories.SolicitudDeAgendaRepository;
import org.matias.demicar.services.AutoServiceI;
import org.matias.demicar.services.SolicitudDeAgendaServiceI;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AutoServiceImpl implements AutoServiceI {

    @Autowired
    private AutoMapperService autoMapper;
    @Autowired
    private SolicitudDeAgendaMapperService solciitudMapper;

    private final AutoRepository autoRepository;
    private final SolicitudDeAgendaRepository solicitudDeAgendaRepository;
    @Autowired
    private SolicitudDeAgendaMapperService solicitudDeAgendaMapperService;

    public AutoServiceImpl(AutoRepository autoRepository,SolicitudDeAgendaRepository solicitudDeAgendaRepository) {
        this.autoRepository = autoRepository;
        this.solicitudDeAgendaRepository = solicitudDeAgendaRepository;
    }

/**/

    @Override
    public List<AutoDto> obtenerAutosPorMatricula(String matricula) {
        List<Auto> autos = autoRepository.findByMatricula(matricula);
        return autos.stream()
                .map(autoMapper::convertToDto)
                .collect(Collectors.toList());
    }
    @Override
    public List<AutoDto> getAutos() {
        List<Auto> autos = (List<Auto>) autoRepository.findAll();
        return autos.stream()
                .map(autoMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AutoDto> obtenerAutoPorId(Long id) {
        Optional<Auto> auto = autoRepository.findById(id);
        return auto.map(autoMapper::convertToDto);
    }


    @Transactional
    @Override
    public AutoDto crearAuto(AutoDto autoDTO) {
        autoDTO.setActivo(true);
        Auto auto = autoMapper.convertToEntity(autoDTO);
        auto.setSolicitudDeAgenda(null);
        autoRepository.save(auto);
        List<SolicitudDeAgendaDto> agendas = autoDTO.getSolicitudDeAgenda();
        List<SolicitudDeAgenda> agendasCautos = new ArrayList<>();
        if (agendas != null) {
            for (SolicitudDeAgendaDto solicitud : agendas) {
                SolicitudDeAgenda agenda = solicitudDeAgendaMapperService.convertToEntity(solicitud);
                agenda.setAuto(auto);
                solicitudDeAgendaRepository.save(agenda);
                agendasCautos.add(agenda);
            }
            auto.setSolicitudDeAgenda(agendasCautos);
        }


        autoRepository.save(auto);

        return autoMapper.convertToDto(auto);
    }

    @Override
    public AutoDto actualizarAuto(Long id, AutoDto autoDTO) {
        Auto auto = autoMapper.convertToEntity(autoDTO);
        auto.setId(id);
        autoRepository.save(auto);
    return autoMapper.convertToDto(auto);
    }

    @Override
    public Optional eliminarAuto(Long id) {
        Auto auto = autoRepository.findById(id).orElse(null);
        auto.setActivo(false);
        autoRepository.save(auto);
        return Optional.of(auto);
    }

    @Override
    public Boolean existByMatricula(String matricula) {
        if (autoRepository.existsByMatricula(matricula)){
            return true;
        }
            return false;
    }

    @Override
    public Boolean existById(Long id) {
        return autoRepository.existsById(id);
    }


}


