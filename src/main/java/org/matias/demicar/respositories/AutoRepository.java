package org.matias.demicar.respositories;

import org.matias.demicar.models.entities.Auto;
import org.matias.demicar.models.entities.Cliente;
import org.matias.demicar.models.entities.SolicitudDeAgenda;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AutoRepository extends CrudRepository<Auto, Long> {
    List<Auto> findByMatricula(String matricula);
    List<Auto> findByMatriculaAndEstado(Cliente cliente);
    List<Auto> findBySolicitudDeAgenda(String matricula, SolicitudDeAgenda solicitudDeAgenda);
    Boolean existsByMatriculaAndEstado(String matricula,String estado);
    Boolean existsById(long id);


}
