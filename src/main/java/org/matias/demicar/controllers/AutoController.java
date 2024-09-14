package org.matias.demicar.controllers;

import jakarta.validation.Valid;
import org.matias.demicar.models.Dtos.AutoDto;
import org.matias.demicar.models.Mappers.AutoMapperService;
import org.matias.demicar.responses.CustomResponse;
import org.matias.demicar.services.AutoServiceI;
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
@RequestMapping("/api/autos")
public class AutoController {

    @Autowired
    AutoServiceI autoService;
    AutoMapperService autoMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(AutoController.class);



    @GetMapping
    public ResponseEntity<CustomResponse<List<AutoDto>>> findAllAutos(@RequestParam(required = false) String matricula) {
        List<String> errores = new ArrayList<>();
        CustomResponse<List<AutoDto>> response = new CustomResponse<>();
        try {
            List<AutoDto> autos = new ArrayList<>();
            if (matricula == null) {
                autos.addAll(autoService.getAutos().stream().filter(c->c.getActivo()).collect(Collectors.toList()));//no pone la matricula, todos
            } else {
                autoService.obtenerAutosPorMatricula(matricula).stream().filter(c->c.getActivo()).forEach(autos::add);//itera al servicio y a los que coindiden a autos
            }
            if (autos.isEmpty() ) { // VACIO ?
                errores.add("No se encontro el auto");
                response.setData(null);
                response.setMessage("No hay autos para mostrar");
                response.setErrors(errores);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

            } else {
                response.setStatus(HttpStatus.OK);
                response.setData(autos);
                response.setMessage("Autos encontrados");
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
    public ResponseEntity<CustomResponse<AutoDto>> save(@Valid @RequestBody AutoDto body, BindingResult bindingResult) {
        CustomResponse<AutoDto> response = new CustomResponse<>();
        List<String> errors = new ArrayList<>();

        // Validar los campos usando BindingResult
        if (bindingResult.hasErrors()) {
            errors.addAll(bindingResult.getFieldErrors().stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.toList()));
        }
        // Validar reglas de negocio
        if (autoService.existByMatricula(body.getMatricula())) {
            errors.add("Nombre ya existe");
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
            // Guardar auto si no hay errores
            AutoDto persistAuto = autoService.crearAuto(body);
            response.setStatus(HttpStatus.OK);
            response.setData(persistAuto);
            response.setMessage("Auto guardado");
            response.setErrors(Collections.emptyList());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Error saving client", e);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setData(null);
            response.setMessage("Ocurrió un error inesperado");
            response.setErrors(Collections.singletonList("Error al guardar el auto"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/{id}")
    public  ResponseEntity<CustomResponse<AutoDto>> update(@PathVariable Long id, @Valid @RequestBody AutoDto body, BindingResult bindingResult) {
        CustomResponse<AutoDto> response = new CustomResponse<>();
        List<String> errors = new ArrayList<>();
        if (bindingResult.hasErrors()) {
            errors.addAll(bindingResult.getFieldErrors().stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.toList()));
        }
        if (!errors.isEmpty()||!autoService.existById(id)) {
            if (!autoService.existById(id)){
                errors.add("El auto no existe");
            }
            response.setErrors(errors);
            response.setMessage("No se pudo actualizar el auto");
            response.setData(null);
            response.setStatus(HttpStatus.CONFLICT);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }
        try {
            // Guardar auto si no hay errores
            AutoDto persistAuto = autoService.actualizarAuto(body.getId(),body);
            response.setStatus(HttpStatus.OK);
            response.setData(persistAuto);
            response.setMessage("Auto actualizado");
            response.setErrors(Collections.emptyList());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Error saving client", e);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setData(null);
            response.setMessage("Ocurrió un error inesperado");
            response.setErrors(Collections.singletonList("Error al guardar el auto"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<AutoDto>> delete(@Valid @PathVariable Long id) {
        CustomResponse<AutoDto> response = new CustomResponse<>();
        List<String> errors = new ArrayList<>();
            try {
                if (autoService.existById(id)) {
                    autoService.eliminarAuto(id);
                    response.setStatus(HttpStatus.OK);
                    response.setData(null);
                    response.setMessage("Auto eliminado");
                    response.setErrors(Collections.emptyList());
                    return new ResponseEntity<>(response, HttpStatus.OK);
                }

                errors.add("El auto no existe");
                response.setErrors(errors);
                response.setMessage("No se pudo eliminar el auto");
                response.setData(null);
                response.setStatus(HttpStatus.CONFLICT);
                return new ResponseEntity<>(response, HttpStatus.CONFLICT);

            }catch (Exception e){
                LOGGER.error("Error saving client", e);
                response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
                response.setData(null);
                response.setMessage("Error al eliminar el auto");
                response.setErrors(Collections.singletonList("Error al eliminar el auto"));
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);


        }
    }


}