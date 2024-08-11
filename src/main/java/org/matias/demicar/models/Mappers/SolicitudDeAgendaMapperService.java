package org.matias.demicar.models.Mappers;

import org.matias.demicar.models.Dtos.SolicitudDeAgendaDto;
import org.matias.demicar.models.entities.SolicitudDeAgenda;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SolicitudDeAgendaMapperService {
@Autowired
    private ModelMapper modelMapper;
    // Método para convertir una entidad a DTO
    public SolicitudDeAgendaDto convertToDto(SolicitudDeAgenda solicitudDeAgenda) {
        return modelMapper.map(solicitudDeAgenda, SolicitudDeAgendaDto.class);
    }

    // Método para convertir un DTO a entidad
    public SolicitudDeAgenda convertToEntity(SolicitudDeAgendaDto solicitudDeAgendaDto) {
        return modelMapper.map(solicitudDeAgendaDto, SolicitudDeAgenda.class);
    }

}
