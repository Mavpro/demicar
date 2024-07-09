package org.matias.demicar.respositories;

import org.matias.demicar.models.entities.Cliente;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ClienteRepository extends CrudRepository<Cliente, Long> {
    List<Cliente> findByNombre(String nombre);
    List<Cliente> findByNombreAndApellido(String nombre, String apellido);
    Boolean existsByNombreAndApellido(String nombre, String apellido);
    Boolean existsByEmail(String email);

}
