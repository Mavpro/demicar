package org.matias.demicar.models.Mappers;

import org.matias.demicar.models.Dtos.ClienteDto;
import org.matias.demicar.models.Dtos.InstructorDto;
import org.matias.demicar.models.entities.Cliente;
import org.matias.demicar.models.entities.Instructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InstructorMapperService {
@Autowired
    private ModelMapper modelMapper;
    // Método para convertir una entidad a DTO
    public InstructorDto convertToDto(Instructor instructor) {
        return modelMapper.map(instructor, InstructorDto.class);
    }

    // Método para convertir un DTO a entidad
    public Instructor convertToEntity(InstructorDto instructorDto) {
        return modelMapper.map(instructorDto, Instructor.class);
    }

}
