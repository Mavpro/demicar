package org.matias.demicar.controllers;

import jakarta.validation.Valid;
import org.matias.demicar.models.Dtos.InstructorDto;
import org.matias.demicar.models.Mappers.InstructorMapperService;
import org.matias.demicar.responses.CustomResponse;
import org.matias.demicar.services.InstructorServiceI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/instructores")
public class InstructorController {

    @Autowired
    InstructorServiceI instructorService;
    InstructorMapperService instructorMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(InstructorController.class);



    @GetMapping
    public ResponseEntity<CustomResponse<List<InstructorDto>>> findAllInstructors(@RequestParam(required = false) String nombre) {
        List<String> errores = new ArrayList<>();
        CustomResponse<List<InstructorDto>> response = new CustomResponse<>();
        try {
            List<InstructorDto> instructors = new ArrayList<>();
            if (nombre == null) {
                instructors.addAll(instructorService.getInstructors().stream().filter(c->c.isActivo()).collect(Collectors.toList()));//no pone el nombre, todos
            } else {
                instructorService.obtenerInstructorPorNombre(nombre).stream().filter(c->c.isActivo()).forEach(instructors::add);//itera al servicio y a los que coindiden a instructors
            }
            if (instructors.isEmpty() ) { // VACIO ?
                errores.add("No se encontro el instructor");
                response.setData(null);
                response.setMessage("No hay instructors para mostrar");
                response.setErrors(errores);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

            } else {
                response.setStatus(HttpStatus.OK);
                response.setData(instructors);
                response.setMessage("Instructors encontrados");
                response.setErrors(errores);
                return new ResponseEntity<>(response, HttpStatus.OK);

            }

        } catch (Exception e) {
            errores.add(e.getMessage());
            response.setErrors(errores);
            response.setMessage(e.getMessage());
            response.setData(Collections.emptyList());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

        }


    }

    @PostMapping
    public ResponseEntity<CustomResponse<InstructorDto>> save(@Valid @RequestBody InstructorDto body, BindingResult bindingResult) {
        CustomResponse<InstructorDto> response = new CustomResponse<>();
        List<String> errors = new ArrayList<>();

        // Validar los campos usando BindingResult
        if (bindingResult.hasErrors()) {
            errors.addAll(bindingResult.getFieldErrors().stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.toList()));
        }
        // Validar reglas de negocio
        if (instructorService.existByNombreyApellido(body.getNombre())) {
            errors.add("Nombre ya existe");
        }
        if (instructorService.existByCorreo(body.getEmail())) {
            errors.add("El email ya existe");
        }
        // Manejar errores si existen
        if (!errors.isEmpty()) {
            response.setErrors(errors);
            response.setMessage("Errores de validación");
            response.setData(null);
            response.setStatus(HttpStatus.CONFLICT);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }
        try {
            // Guardar instructor si no hay errores
            InstructorDto persistInstructor = instructorService.crearInstructor(body);
            response.setStatus(HttpStatus.OK);
            response.setData(persistInstructor);
            response.setMessage("Instructor guardado");
            response.setErrors(Collections.emptyList());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Error saving client", e);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setData(null);
            response.setMessage("Ocurrió un error inesperado");
            response.setErrors(Collections.singletonList("Error al guardar el instructor"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/{id}")
    public  ResponseEntity<CustomResponse<InstructorDto>> update(@PathVariable Long id, @Valid @RequestBody InstructorDto body, BindingResult bindingResult) {
        CustomResponse<InstructorDto> response = new CustomResponse<>();
        List<String> errors = new ArrayList<>();
        if (bindingResult.hasErrors()) {
            errors.addAll(bindingResult.getFieldErrors().stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.toList()));
        }
        if (!errors.isEmpty()||!instructorService.existById(id)) {
            if (!instructorService.existById(id)){
                errors.add("El instructor no existe");
            }
            response.setErrors(errors);
            response.setMessage("No se pudo actualizar el instructor");
            response.setData(null);
            response.setStatus(HttpStatus.CONFLICT);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }
        try {
            // Guardar instructor si no hay errores
            InstructorDto persistInstructor = instructorService.actualizarInstructor(body.getId(),body);
            response.setStatus(HttpStatus.OK);
            response.setData(persistInstructor);
            response.setMessage("Instructor actualizado");
            response.setErrors(Collections.emptyList());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Error saving client", e);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setData(null);
            response.setMessage("Ocurrió un error inesperado");
            response.setErrors(Collections.singletonList("Error al guardar el instructor"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<InstructorDto>> delete(@Valid @PathVariable Long id) {
        CustomResponse<InstructorDto> response = new CustomResponse<>();
        List<String> errors = new ArrayList<>();
            try {
                if (instructorService.existById(id)) {
                    instructorService.eliminarInstructor(id);
                    response.setStatus(HttpStatus.OK);
                    response.setData(null);
                    response.setMessage("Instructor eliminado");
                    response.setErrors(Collections.emptyList());
                    return new ResponseEntity<>(response, HttpStatus.OK);
                }

                errors.add("El instructor no existe");
                response.setErrors(errors);
                response.setMessage("No se pudo eliminar el instructor");
                response.setData(null);
                response.setStatus(HttpStatus.CONFLICT);
                return new ResponseEntity<>(response, HttpStatus.CONFLICT);

            }catch (Exception e){
                LOGGER.error("Error saving client", e);
                response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
                response.setData(null);
                response.setMessage("Error al eliminar el instructor");
                response.setErrors(Collections.singletonList("Error al eliminar el instructor"));
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);


        }
    }


}