package org.matias.demicar.services.impl;

import org.matias.demicar.models.Dtos.InstructorDto;
import org.matias.demicar.models.Mappers.InstructorMapperService;
import org.matias.demicar.models.entities.Auto;
import org.matias.demicar.models.entities.Instructor;
import org.matias.demicar.respositories.InstructorRepository;
import org.matias.demicar.services.InstructorServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InstructorServiceImpl implements InstructorServiceI {

    @Autowired
    private final InstructorMapperService instructorMapper;
    private final InstructorRepository instructorRepository;

    public InstructorServiceImpl(InstructorMapperService instructorMapper, InstructorRepository instructorRepository) {
        this.instructorMapper = instructorMapper;
        this.instructorRepository = instructorRepository;
    }


    @Override
    public List<InstructorDto> getInstructors() {
        List<Instructor> instructors = (List<Instructor>) instructorRepository.findAll();
        return instructors.stream()
                .map(instructorMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<InstructorDto> obtenerInstructorPorId(Long id) {
Optional<Instructor> instructor = instructorRepository.findById(id);
    return instructor.map(instructorMapper::convertToDto);
    }

    @Override
    public List<InstructorDto> obtenerInstructorPorNombre(String nombre) {
        List<Instructor> instructor = instructorRepository.findByNombre(nombre);
        return instructor.stream()
                .map(instructorMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public InstructorDto crearInstructor(InstructorDto instructorDTO) {
        System.out.println(instructorDTO);
        instructorDTO.setActivo(true);
       Instructor instructor =  instructorRepository.save(instructorMapper.convertToEntity(instructorDTO));
       return instructorMapper.convertToDto(instructor);
    }

    @Override
    public InstructorDto actualizarInstructor(Long id, InstructorDto instructorDTO) {

        Instructor inst = instructorMapper.convertToEntity(instructorDTO);
        inst.setId(id);
        instructorRepository.save(inst);
        return instructorMapper.convertToDto(inst);

    }

    @Override
    public Optional eliminarInstructor(Long id) {
        Instructor instructor = instructorRepository.findById(id).orElse(null);
        instructor.setActivo(false);
        instructorRepository.save(instructor);
        return Optional.of(instructorMapper.convertToDto(instructor));
    }

    @Override
    public Boolean existByNombreyApellido(String nombre_y_apellido){
        return instructorRepository.existsByNombre(nombre_y_apellido);
    }

    @Override
    public Boolean existByCorreo(String correo) {
        return instructorRepository.existsByEmail(correo);
    }

    @Override
    public Boolean existById(Long id) {
        return instructorRepository.existsById(id);
    }


}


