package org.matias.demicar.respositories;

import org.matias.demicar.models.entities.Coordinador;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CoordinadorRepository extends CrudRepository<Coordinador, Long> {
    List<Coordinador> findByNombre(String nonmbre);
    Boolean existsByNombre(String nombre);
    Boolean existsByEmail(String email);


}
