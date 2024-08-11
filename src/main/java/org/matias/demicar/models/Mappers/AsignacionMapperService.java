package org.matias.demicar.models.Mappers;

import org.matias.demicar.models.Dtos.AsignacionDto;
import org.matias.demicar.models.entities.Asignacion;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AsignacionMapperService {
    @Autowired
    private ModelMapper modelMapper;
    // Método para convertir una entidad a DTO
    public AsignacionDto convertToDto(Asignacion asignacion) {
        return modelMapper.map(asignacion, AsignacionDto.class);
    }

    // Método para convertir un DTO a entidad
    public Asignacion convertToEntity(AsignacionDto asignacionDto) {
        return modelMapper.map(asignacionDto, Asignacion.class);
    }

}
