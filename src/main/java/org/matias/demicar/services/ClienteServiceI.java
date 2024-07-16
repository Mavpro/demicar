package org.matias.demicar.services;

import jakarta.persistence.Id;
import org.matias.demicar.models.Dtos.ClienteDto;

import java.util.List;
import java.util.Optional;

public interface ClienteServiceI {
List<ClienteDto> getClientes();
    Optional<ClienteDto> obtenerClientePorId(Long id);

    List<ClienteDto> obtenerClientePorNombre(String nombre);

    ClienteDto crearCliente(ClienteDto clienteDTO);

    ClienteDto actualizarCliente(Long id, ClienteDto clienteDTO);

    Optional eliminarCliente(Long id);

    Boolean existByNombreyApellido(String nombre);

    Boolean existByCorreo(String correo);

    Boolean existById(Long id);

}
