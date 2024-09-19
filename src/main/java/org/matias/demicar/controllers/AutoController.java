package org.matias.demicar.controllers;

import jakarta.validation.Valid;
import org.matias.demicar.exeptions.ConstraintsViolation;
import org.matias.demicar.models.Dtos.AutoDto;
import org.matias.demicar.models.Dtos.ClienteDto;
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
        List<AutoDto> autos = new ArrayList<>();

        if (matricula == null) {
            autos.addAll(autoService.getAutos().stream().filter(c -> c.getActivo()).collect(Collectors.toList()));//no pone la matricula, todos
            response.setStatus(HttpStatus.OK);
            response.setData(autos);
            response.setMessage("Autos encontrados con exito.");
            response.setErrors(errores);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            autoService.obtenerAutosPorMatricula(matricula).stream().filter(c -> c.getActivo()).forEach(autos::add);//itera al servicio y a los que coindiden a autos
            response.setStatus(HttpStatus.OK);
            response.setData(autos);
            response.setMessage("Autos encontrados con la matricula :" + matricula + "con exito.");
            response.setErrors(errores);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

    }

    @PostMapping
    public ResponseEntity<CustomResponse<AutoDto>> save(@Valid @RequestBody AutoDto body, BindingResult bindingResult) {
        CustomResponse<AutoDto> response = new CustomResponse<>();
        List<String> errors = new ArrayList<>();
        errors = bindingResult.getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());

        // Validar los campos usando BindingResult
        if (bindingResult.hasErrors()) {
            errors.addAll(bindingResult.getFieldErrors().stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.toList()));
        }
        if (bindingResult.hasErrors()) {
            throw new ConstraintsViolation(errors);
        }
        AutoDto autoPersist = autoService.crearAuto(body);
        response = new CustomResponse<>(
                HttpStatus.OK, "Auto guardado", autoPersist, Collections.emptyList()
        );
        return new ResponseEntity<>(response, HttpStatus.OK);


    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<AutoDto>> update(@PathVariable Long id, @Valid @RequestBody AutoDto body, BindingResult bindingResult) {
        List<String> errors = new ArrayList<>();
        errors = bindingResult.getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());
        if (bindingResult.hasErrors()) {
            throw new ConstraintsViolation(errors);
        } // No se valida en el servicio porque binding es con el DTO del controller, pero despues si

        AutoDto autoPersist = autoService.actualizarAuto(id, body);

        // Construir la respuesta
        CustomResponse<AutoDto> response = new CustomResponse<>();
        response.setStatus(HttpStatus.OK);
        response.setData(autoPersist);
        response.setMessage("Auto actualizado");
        response.setErrors(Collections.emptyList());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<AutoDto>> delete(@Valid @PathVariable Long id) {
        CustomResponse<AutoDto> response = new CustomResponse<>();
        List<String> errors = new ArrayList<>();
        autoService.eliminarAuto(id);
        response.setStatus(HttpStatus.OK);
        response.setData(null);
        response.setMessage("Autocon id: " + id + " fue eliminado.");
        response.setErrors(Collections.emptyList());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

        }