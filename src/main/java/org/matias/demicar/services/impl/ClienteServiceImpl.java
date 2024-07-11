package org.matias.demicar.services.impl;

import org.matias.demicar.models.Dtos.ClienteDto;

import org.matias.demicar.models.Mappers.ClienteMapperService;
import org.matias.demicar.models.entities.Cliente;
import org.matias.demicar.respositories.ClienteRepository;
import org.matias.demicar.services.ClienteServiceI;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteServiceImpl implements ClienteServiceI {

    @Autowired
    private ClienteMapperService clienteMapper;
    private final ClienteRepository clienteRepository;

    public ClienteServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
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

    @Override
    public ClienteDto crearCliente(ClienteDto clienteDTO) {
        System.out.println(clienteDTO);
       Cliente cliente =  clienteRepository.save(clienteMapper.convertToEntity(clienteDTO));
       return clienteMapper.convertToDto(cliente);
    }

    @Override
    public ClienteDto actualizarCliente(Long id, ClienteDto clienteDTO) {
        return null;
    }

    @Override
    public void eliminarCliente(Long id) {

    }

    @Override
    public Boolean existByNombreyApellido(String nombre_y_apellido){
        return clienteRepository.existsByNombreApellido(nombre_y_apellido);
    }

    @Override
    public Boolean existByCorreo(String correo) {
        return clienteRepository.existsByEmail(correo);
    }


    }


