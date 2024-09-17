package org.matias.demicar.services.impl;

import jakarta.transaction.Transactional;
import org.matias.demicar.exeptions.ResourceNotFoundException;
import org.matias.demicar.exeptions.ValidationException;
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
        if (clientes.isEmpty()) {
            throw new ResourceNotFoundException("No hay clientes ingresados");
        }
        return clientes.stream()
                .map(clienteMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ClienteDto> obtenerClientePorId(Long id) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(id);
        if (clienteOptional.isEmpty()) {
            throw new ResourceNotFoundException("Cliente con id " + id + " no encontrado");
        }
        Cliente cliente = clienteOptional.get();
        return Optional.of(clienteMapper.convertToDto(cliente));
    }

    @Override
    public List<ClienteDto> obtenerClientePorNombre(String nombre) {
        List<Cliente> clientes = clienteRepository.findByNombreApellido(nombre);
        if (clientes.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron clientes con el nombre: " + nombre);
        }

        return clientes.stream()
                .map(clienteMapper::convertToDto)
                .collect(Collectors.toList());
    }
    @Transactional
    @Override
    public ClienteDto crearCliente(ClienteDto clienteDTO) {

        clienteDTO.setActivo(true);
        Cliente cliente = clienteMapper.convertToEntity(clienteDTO);
        cliente.setSolicitudesDeAgenda(null);

        List<String> errors = new ArrayList<>();

        // Validar reglas de negocio
        if (clienteRepository.existsByNombreApellido(clienteDTO.getNombreApellido())) {
            errors.add("El nombre y apellido ya existe");
        }
        if (clienteRepository.existsByEmail(clienteDTO.getEmail())) {
            errors.add("El email ya existe");
        }

        // Lanza excepción si hay errores
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }

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

        List<String> errors = new ArrayList<>();

        // Verificar si el cliente existe
        if(!clienteRepository.existsById(id)) {
                throw new ResourceNotFoundException("El cliente con id " + id + " no existe");
        }
        // Convertir el DTO a entidad
        Cliente cliente = clienteMapper.convertToEntity(clienteDTO);

        // Establecer el ID del cliente a actualizar
        cliente.setId(id);

        // Guardar la entidad actualizada
        clienteRepository.save(cliente);

        // Devolver el DTO del cliente actualizado
        return clienteMapper.convertToDto(cliente);
    }

    @Override
    public Optional eliminarCliente(Long id) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("El cliente con id " + id + " no existe"));
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


