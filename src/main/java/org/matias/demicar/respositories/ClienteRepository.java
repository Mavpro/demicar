package org.matias.demicar.respositories;

import org.matias.demicar.models.entities.Cliente;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.swing.text.StyledEditorKit;
import java.util.List;

public interface ClienteRepository extends CrudRepository<Cliente, Long> {
    List<Cliente> findByNombreApellido(String nonmbreApellido);
    List<Cliente> findByActivo(boolean activo);
    Boolean existsByNombreApellido(String nombreApellido);
    Boolean existsByEmail(String email);


}
