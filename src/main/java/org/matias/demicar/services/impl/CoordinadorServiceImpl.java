package org.matias.demicar.services.impl;

import org.matias.demicar.models.Dtos.CoordinadorDto;
import org.matias.demicar.models.Mappers.CoordinadorMapperService;
import org.matias.demicar.models.entities.Coordinador;
import org.matias.demicar.respositories.CoordinadorRepository;
import org.matias.demicar.services.CoordinadorServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CoordinadorServiceImpl implements CoordinadorServiceI {

    @Autowired
    private final CoordinadorMapperService coordinadorMapper;
    private final CoordinadorRepository coordinadorRepository;

    public CoordinadorServiceImpl(CoordinadorMapperService coordinadorMapper, CoordinadorRepository coordinadorRepository) {
        this.coordinadorMapper = coordinadorMapper;
        this.coordinadorRepository = coordinadorRepository;
    }


    @Override
    public List<CoordinadorDto> getCoordinadors() {
        List<Coordinador> coordinadors = (List<Coordinador>) coordinadorRepository.findAll();
        return coordinadors.stream()
                .map(coordinadorMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CoordinadorDto> obtenerCoordinadorPorId(Long id) {
Optional<Coordinador> coordinador = coordinadorRepository.findById(id);
    return coordinador.map(coordinadorMapper::convertToDto);
    }

    @Override
    public List<CoordinadorDto> obtenerCoordinadorPorNombre(String nombre) {
        List<Coordinador> coordinador = coordinadorRepository.findByNombre(nombre);
        return coordinador.stream()
                .map(coordinadorMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CoordinadorDto crearCoordinador(CoordinadorDto coordinadorDTO) {
        System.out.println(coordinadorDTO);
        coordinadorDTO.setActivo(true);
       Coordinador coordinador =  coordinadorRepository.save(coordinadorMapper.convertToEntity(coordinadorDTO));
       return coordinadorMapper.convertToDto(coordinador);
    }

    @Override
    public CoordinadorDto actualizarCoordinador(Long id, CoordinadorDto coordinadorDTO) {

return null;

    }

    @Override
    public Optional eliminarCoordinador(Long id) {
        Coordinador coordinador = coordinadorRepository.findById(id).orElse(null);
        coordinador.setActivo(false);
        coordinadorRepository.save(coordinador);
        return Optional.of(coordinador);
    }

    @Override
    public Boolean existByNombreyApellido(String nombre_y_apellido){
        return coordinadorRepository.existsByNombre(nombre_y_apellido);
    }

    @Override
    public Boolean existByCorreo(String correo) {
        return coordinadorRepository.existsByEmail(correo);
    }

    @Override
    public Boolean existById(Long id) {
        return coordinadorRepository.existsById(id);
    }


}


