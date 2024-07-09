package org.matias.demicar.services;

import org.matias.demicar.models.Dtos.ClienteDto;

import java.util.List;
import java.util.Optional;

public interface ClienteServiceI {
List<ClienteDto> getClientes();
    Optional<ClienteDto> obtenerClientePorId(Long id);

    List<ClienteDto> obtenerClientePorNombre(String nombre);

    ClienteDto crearCliente(ClienteDto clienteDTO);

    ClienteDto actualizarCliente(Long id, ClienteDto clienteDTO);

    void eliminarCliente(Long id);

    Boolean existByNombre(String nombre);

    Boolean existByCorreo(String correo);

}
