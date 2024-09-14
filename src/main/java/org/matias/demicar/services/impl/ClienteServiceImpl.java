package org.matias.demicar.services.impl;

import jakarta.transaction.Transactional;
import org.matias.demicar.models.Dtos.ClienteDto;

import org.matias.demicar.models.Dtos.SolicitudDeAgendaDto;
import org.matias.demicar.models.Mappers.ClienteMapperService;
import org.matias.demicar.models.Mappers.SolicitudDeAgendaMapperService;
import org.matias.demicar.models.entities.Cliente;
import org.matias.demicar.models.entities.SolicitudDeAgenda;
import org.matias.demicar.respositories.ClienteRepository;
import org.matias.demicar.respositories.SolicitudDeAgendaRepository;
import org.matias.demicar.services.ClienteServiceI;
import org.matias.demicar.services.SolicitudDeAgendaServiceI;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteServiceImpl implements ClienteServiceI {

    @Autowired
    private ClienteMapperService clienteMapper;
    @Autowired
    private SolicitudDeAgendaMapperService solciitudMapper;

    private final ClienteRepository clienteRepository;
    private final SolicitudDeAgendaRepository solicitudDeAgendaRepository;
    @Autowired
    private SolicitudDeAgendaMapperService solicitudDeAgendaMapperService;

    public ClienteServiceImpl(ClienteRepository clienteRepository,SolicitudDeAgendaRepository solicitudDeAgendaRepository) {
        this.clienteRepository = clienteRepository;
        this.solicitudDeAgendaRepository = solicitudDeAgendaRepository;
    }



    @Override
    public List<ClienteDto> getClientes() {
        List<Cliente> clientes = (List<Cliente>) clienteRepository.findAll();
        return clientes.stream()
                .map(clienteMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ClienteDto> obtenerClientePorId(Long id) {
Optional<Cliente> cliente = clienteRepository.findById(id);
    return cliente.map(clienteMapper::convertToDto);
    }

    @Override
    public List<ClienteDto> obtenerClientePorNombre(String nombre) {
        List<Cliente> cliente = clienteRepository.findByNombreApellido(nombre);
        return cliente.stream()
                .map(clienteMapper::convertToDto)
                .collect(Collectors.toList());
    }
    @Transactional
    @Override
    public ClienteDto crearCliente(ClienteDto clienteDTO) {
        clienteDTO.setActivo(true);
        Cliente cliente = clienteMapper.convertToEntity(clienteDTO);
        cliente.setSolicitudesDeAgenda(null);
        clienteRepository.save(cliente);
        List<SolicitudDeAgendaDto> agendas = clienteDTO.getSolicitudesDeAgenda();
        List<SolicitudDeAgenda> agendasCclientes = new ArrayList<>();
        for (SolicitudDeAgendaDto solicitud : agendas) {
            SolicitudDeAgenda agenda = solicitudDeAgendaMapperService.convertToEntity(solicitud);
            agenda.setCliente(cliente);
            solicitudDeAgendaRepository.save(agenda);
            agendasCclientes.add(agenda);
        }

        cliente.setSolicitudesDeAgenda(agendasCclientes);
        clienteRepository.save(cliente);

      return clienteMapper.convertToDto(cliente);
    }

    @Override
    public ClienteDto actualizarCliente(Long id, ClienteDto clienteDTO) {

        Cliente cliente = clienteMapper.convertToEntity(clienteDTO);
        cliente.setId(id);
        clienteRepository.save(cliente);
     return clienteMapper.convertToDto(cliente);

    }

    @Override
    public Optional eliminarCliente(Long id) {
        Cliente cliente = clienteRepository.findById(id).orElse(null);
        cliente.setActivo(false);
        clienteRepository.save(cliente);
        return Optional.of(clienteMapper.convertToDto(cliente));
    }

    @Override
    public Boolean existByNombreyApellido(String nombre_y_apellido){
        return clienteRepository.existsByNombreApellido(nombre_y_apellido);
    }

    @Override
    public Boolean existByCorreo(String correo) {
        return clienteRepository.existsByEmail(correo);
    }

    @Override
    public Boolean existById(Long id) {
        return clienteRepository.existsById(id);
    }


}


