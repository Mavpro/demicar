package org.matias.demicar.controllers;

import jakarta.validation.Valid;
import jakarta.validation.Validation;
import org.matias.demicar.models.Dtos.ClienteDto;
import org.matias.demicar.models.Mappers.ClienteMapperService;
import org.matias.demicar.models.entities.Cliente;
import org.matias.demicar.responses.CustomResponse;
import org.matias.demicar.services.ClienteServiceI;
import org.matias.demicar.validator.BindingValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.LazyInitializationExcludeFilter;

import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.naming.Binding;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    ClienteServiceI clienteService;
    ClienteMapperService clienteMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(ClienteController.class);



    @GetMapping
    public ResponseEntity<CustomResponse<List<ClienteDto>>> findAllClientes(@RequestParam(required = false) String nombre) {
        List<String> errores = new ArrayList<>();
        CustomResponse<List<ClienteDto>> response = new CustomResponse<>();
        try {
            List<ClienteDto> clientes = new ArrayList<>();
            if (nombre == null) {
                clientes.addAll(clienteService.getClientes());//no pone el nombre, todos
            } else {
                clienteService.obtenerClientePorNombre(nombre).forEach(clientes::add);//itera al servicio y a los que coindiden a clientes
            }
            if (clientes.isEmpty() || clientes.size() == 0) { // VACIO ?
                errores.add("No se encontro el cliente");
                response.setData(null);
                response.setMessage("No hay clientes para mostrar");
                response.setErrors(errores);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

            } else {
                response.setStatus(HttpStatus.OK);
                response.setData(clientes);
                response.setMessage("Clientes encontrados");
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
    public ResponseEntity<CustomResponse<ClienteDto>> save(@Valid @RequestBody ClienteDto body, BindingResult bindingResult) {
        CustomResponse<ClienteDto> response = new CustomResponse<>();
        List<String> errors = new ArrayList<>();

        // Validar los campos usando BindingResult
        if (bindingResult.hasErrors()) {
            errors.addAll(bindingResult.getFieldErrors().stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.toList()));
        }

        // Validar reglas de negocio
        if (clienteService.existByNombreyApellido(body.getNombreApellido())) {
            errors.add("Nombre ya existe");
        }
        if (clienteService.existByCorreo(body.getEmail())) {
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
            // Guardar cliente si no hay errores
            ClienteDto persistCliente = clienteService.crearCliente(body);
            response.setStatus(HttpStatus.OK);
            response.setData(persistCliente);
            response.setMessage("Cliente guardado");
            response.setErrors(Collections.emptyList());
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            LOGGER.error("Error saving client", e);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setData(null);
            response.setMessage("Ocurrió un error inesperado");
            response.setErrors(Collections.singletonList("Error al guardar el cliente"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}