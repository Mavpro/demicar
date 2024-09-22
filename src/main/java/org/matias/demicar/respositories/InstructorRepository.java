package org.matias.demicar.respositories;

import org.matias.demicar.models.entities.Instructor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface InstructorRepository extends CrudRepository<Instructor, Long> {
    List<Instructor> findByNombre(String nonmbre);
    Boolean existsByNombre(String nombre);
    Boolean existsByEmail(String email);
    List<Instructor> findByActivo(boolean activo);


}
