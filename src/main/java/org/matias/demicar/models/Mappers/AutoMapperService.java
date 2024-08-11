package org.matias.demicar.models.Mappers;

import org.matias.demicar.models.Dtos.AutoDto;
import org.matias.demicar.models.entities.Auto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutoMapperService {
    @Autowired
    private ModelMapper modelMapper;
    // Método para convertir una entidad a DTO
    public AutoDto convertToDto(Auto auto) {
        return modelMapper.map(auto, AutoDto.class);
    }

    // Método para convertir un DTO a entidad
    public Auto convertToEntity(AutoDto autoDto) {
        return modelMapper.map(autoDto, Auto.class);
    }

}