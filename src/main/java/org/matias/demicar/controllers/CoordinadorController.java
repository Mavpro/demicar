package org.matias.demicar.controllers;

import jakarta.validation.Valid;
import org.matias.demicar.models.Dtos.CoordinadorDto;
import org.matias.demicar.models.Mappers.CoordinadorMapperService;
import org.matias.demicar.responses.CustomResponse;
import org.matias.demicar.services.CoordinadorServiceI;
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
@RequestMapping("/api/coordinadores")
public class CoordinadorController {

    @Autowired
    CoordinadorServiceI coordinadorService;
    CoordinadorMapperService coordinadorMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(CoordinadorController.class);



    @GetMapping
    public ResponseEntity<CustomResponse<List<CoordinadorDto>>> findAllCoordinadors(@RequestParam(required = false) String nombre) {
        List<String> errores = new ArrayList<>();
        CustomResponse<List<CoordinadorDto>> response = new CustomResponse<>();
        try {
            List<CoordinadorDto> coordinadors = new ArrayList<>();
            if (nombre == null) {
                coordinadors.addAll(coordinadorService.getCoordinadors().stream().filter(c->c.isActivo()).collect(Collectors.toList()));//no pone el nombre, todos
            } else {
                coordinadorService.obtenerCoordinadorPorNombre(nombre).stream().filter(c->c.isActivo()).forEach(coordinadors::add);//itera al servicio y a los que coindiden a coordinadors
            }
            if (coordinadors.isEmpty() ) { // VACIO ?
                errores.add("No se encontro el coordinador");
                response.setData(null);
                response.setMessage("No hay coordinadors para mostrar");
                response.setErrors(errores);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

            } else {
                response.setStatus(HttpStatus.OK);
                response.setData(coordinadors);
                response.setMessage("Coordinadors encontrados");
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
    public ResponseEntity<CustomResponse<CoordinadorDto>> save(@Valid @RequestBody CoordinadorDto body, BindingResult bindingResult) {
        CustomResponse<CoordinadorDto> response = new CustomResponse<>();
        List<String> errors = new ArrayList<>();

        // Validar los campos usando BindingResult
        if (bindingResult.hasErrors()) {
            errors.addAll(bindingResult.getFieldErrors().stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.toList()));
        }
        // Validar reglas de negocio
        if (coordinadorService.existByNombreyApellido(body.getNombre())) {
            errors.add("Nombre ya existe");
        }
        if (coordinadorService.existByCorreo(body.getEmail())) {
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
            // Guardar coordinador si no hay errores
            CoordinadorDto persistCoordinador = coordinadorService.crearCoordinador(body);
            response.setStatus(HttpStatus.OK);
            response.setData(persistCoordinador);
            response.setMessage("Coordinador guardado");
            response.setErrors(Collections.emptyList());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Error saving client", e);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setData(null);
            response.setMessage("Ocurrió un error inesperado");
            response.setErrors(Collections.singletonList("Error al guardar el coordinador"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/{id}")
    public  ResponseEntity<CustomResponse<CoordinadorDto>> update(@PathVariable Long id, @Valid @RequestBody CoordinadorDto body, BindingResult bindingResult) {
        CustomResponse<CoordinadorDto> response = new CustomResponse<>();
        List<String> errors = new ArrayList<>();
        if (bindingResult.hasErrors()) {
            errors.addAll(bindingResult.getFieldErrors().stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.toList()));
        }
        if (!errors.isEmpty()||!coordinadorService.existById(id)) {
            if (!coordinadorService.existById(body.getId())){
                errors.add("El coordinador no existe");
            }
            response.setErrors(errors);
            response.setMessage("No se pudo actualizar el coordinador");
            response.setData(null);
            response.setStatus(HttpStatus.CONFLICT);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }
        try {
            // Guardar coordinador si no hay errores
            CoordinadorDto persistCoordinador = coordinadorService.actualizarCoordinador(body.getId(),body);
            response.setStatus(HttpStatus.OK);
            response.setData(persistCoordinador);
            response.setMessage("Coordinador actualizado");
            response.setErrors(Collections.emptyList());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Error saving client", e);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setData(null);
            response.setMessage("Ocurrió un error inesperado");
            response.setErrors(Collections.singletonList("Error al guardar el coordinador"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<CoordinadorDto>> delete(@Valid @PathVariable Long id) {
        CustomResponse<CoordinadorDto> response = new CustomResponse<>();
        List<String> errors = new ArrayList<>();
            try {
                if (coordinadorService.existById(id)) {
                    coordinadorService.eliminarCoordinador(id);
                    response.setStatus(HttpStatus.OK);
                    response.setData(null);
                    response.setMessage("Coordinador eliminado");
                    response.setErrors(Collections.emptyList());
                    return new ResponseEntity<>(response, HttpStatus.OK);
                }

                errors.add("El coordinador no existe");
                response.setErrors(errors);
                response.setMessage("No se pudo eliminar el coordinador");
                response.setData(null);
                response.setStatus(HttpStatus.CONFLICT);
                return new ResponseEntity<>(response, HttpStatus.CONFLICT);

            }catch (Exception e){
                LOGGER.error("Error saving client", e);
                response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
                response.setData(null);
                response.setMessage("Error al eliminar el coordinador");
                response.setErrors(Collections.singletonList("Error al eliminar el coordinador"));
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);


        }
    }


}