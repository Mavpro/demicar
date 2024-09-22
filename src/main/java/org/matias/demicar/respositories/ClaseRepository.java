package org.matias.demicar.respositories;

import org.matias.demicar.models.entities.Auto;
import org.matias.demicar.models.entities.Clase;
import org.matias.demicar.models.entities.SolicitudDeAgenda;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ClaseRepository extends CrudRepository<Clase, Long> {
        Boolean existsById(long id);



}
