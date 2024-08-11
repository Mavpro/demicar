package org.matias.demicar.models.Mappers;

import org.matias.demicar.models.Dtos.ClienteDto;
import org.matias.demicar.models.entities.Cliente;
import org.matias.demicar.respositories.ClienteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteMapperService {
@Autowired
    private ModelMapper modelMapper;
    // Método para convertir una entidad a DTO
    public ClienteDto convertToDto(Cliente cliente) {
        return modelMapper.map(cliente, ClienteDto.class);
    }

    // Método para convertir un DTO a entidad
    public Cliente convertToEntity(ClienteDto clienteDto) {
        return modelMapper.map(clienteDto, Cliente.class);
    }

}
