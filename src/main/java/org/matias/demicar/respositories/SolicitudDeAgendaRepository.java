package org.matias.demicar.respositories;

import org.matias.demicar.models.entities.Cliente;
import org.matias.demicar.models.entities.Instructor;
import org.matias.demicar.models.entities.SolicitudDeAgenda;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface SolicitudDeAgendaRepository extends CrudRepository<SolicitudDeAgenda, Long> {
    List<SolicitudDeAgenda> findByCliente_Id (Long clienteId);
    List<SolicitudDeAgenda> findByInstructor_Id (Long instructorId);
    List<SolicitudDeAgenda> findAllByInstructorIdAndFechaClaseBetween(Long instructorId, LocalDateTime fechaInicio, LocalDateTime fechaFin);
    List<SolicitudDeAgenda> findAllByCliente_IdAndFechaClaseBetween(Long id, LocalDateTime fechaClase, LocalDateTime fechaClase2);
    List<SolicitudDeAgenda> findAllByAuto_IdAndFechaClaseBetween(Long id, LocalDateTime fechaClase, LocalDateTime fechaClase2);

}
