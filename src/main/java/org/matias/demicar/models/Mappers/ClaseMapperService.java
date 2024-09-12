package org.matias.demicar.models.Mappers;

import org.matias.demicar.models.Dtos.ClaseDto;
import org.matias.demicar.models.entities.Clase;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClaseMapperService {
    @Autowired
    private ModelMapper modelMapper;
    // Método para convertir una entidad a DTO
    public ClaseDto convertToDto(Clase clase) {
        return modelMapper.map(clase, ClaseDto.class);
    }

    // Método para convertir un DTO a entidad
    public Clase convertToEntity(Clase claseDto) {
        return modelMapper.map(claseDto, Clase.class);
    }

}