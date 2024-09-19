package org.matias.demicar.services.impl;

import org.matias.demicar.exeptions.ErrorServerExeption;
import org.matias.demicar.exeptions.ResourceNotFoundException;
import org.matias.demicar.exeptions.ValidationException;
import org.matias.demicar.models.Dtos.CoordinadorDto;
import org.matias.demicar.models.Mappers.CoordinadorMapperService;
import org.matias.demicar.models.entities.Coordinador;
import org.matias.demicar.respositories.CoordinadorRepository;
import org.matias.demicar.services.CoordinadorServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.security.GeneralSecurityException;
import java.util.ArrayList;
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
        if(coordinadors.isEmpty()) {
            throw new ResourceNotFoundException("No hay coordinadores ingresados.");
        }
        return coordinadors.stream()
                .map(coordinadorMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CoordinadorDto> obtenerCoordinadorPorId(Long id) {
Optional<Coordinador> coordinador = coordinadorRepository.findById(id);
if(coordinador.isPresent()) {
    throw new ResourceNotFoundException("Coordinador " + id + " no encontrado.");
}
    return coordinador.map(coordinadorMapper::convertToDto);
    }

    @Override
    public List<CoordinadorDto> obtenerCoordinadorPorNombre(String nombre) {
        List<Coordinador> coordinador = coordinadorRepository.findByNombre(nombre);
        if(coordinador.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron coordinadores con el nombre" +nombre+".");
        }
        return coordinador.stream()
                .map(coordinadorMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CoordinadorDto crearCoordinador(CoordinadorDto coordinadorDTO) {
       coordinadorDTO.setActivo(true);

        List<String> errors = new ArrayList<>();
        // Validar reglas de negocio
        if (coordinadorRepository.existsByNombre(coordinadorDTO.getNombre())) {
            errors.add("El nombre y apellido ya existe");
        }
        if (coordinadorRepository.existsByEmail(coordinadorDTO.getEmail())) {
            errors.add("El email ya existe");
        }
        // Lanza excepción si hay errores
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);

        }
            try {
                Coordinador coordinador =  coordinadorRepository.save(coordinadorMapper.convertToEntity(coordinadorDTO));
                return coordinadorMapper.convertToDto(coordinador) ;
            }catch(Exception e){
                     throw new ErrorServerExeption("Error al guardar en el serv");

            }



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
        return Optional.of(coordinadorMapper.convertToDto(coordinador));
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


