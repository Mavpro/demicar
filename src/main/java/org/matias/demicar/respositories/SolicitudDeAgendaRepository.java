package org.matias.demicar.respositories;

import org.matias.demicar.models.entities.Cliente;
import org.matias.demicar.models.entities.SolicitudDeAgenda;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SolicitudDeAgendaRepository extends CrudRepository<SolicitudDeAgenda, Long> {
    List<SolicitudDeAgenda> findByCliente (Cliente cliente);

}
