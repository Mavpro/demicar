package org.matias.demicar.models.Mappers;

import org.matias.demicar.models.Dtos.CoordinadorDto;
import org.matias.demicar.models.entities.Coordinador;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoordinadorMapperService {
@Autowired
    private ModelMapper modelMapper;
    // Método para convertir una entidad a DTO
    public CoordinadorDto convertToDto(Coordinador coordinador) {
        return modelMapper.map(coordinador, CoordinadorDto.class);
    }

    // Método para convertir un DTO a entidad
    public Coordinador convertToEntity(CoordinadorDto coordinadorDto) {
        return modelMapper.map(coordinadorDto, Coordinador.class);
    }

}
