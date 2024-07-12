package org.matias.demicar.controllers;

import org.matias.demicar.models.Dtos.ClienteDto;
import org.matias.demicar.models.Mappers.ClienteMapperService;
import org.matias.demicar.models.entities.Cliente;
import org.matias.demicar.responses.CustomResponse;
import org.matias.demicar.services.ClienteServiceI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.ssl.DefaultSslBundleRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    public ResponseEntity<CustomResponse<ClienteDto>> save(@RequestBody ClienteDto body) {
        CustomResponse<ClienteDto> response = new CustomResponse<>();
        List<String> errors = new ArrayList<>(); // Create an empty list to store errors

        try {
            // Check for existing client with same email
            if (clienteService.existByCorreo(body.getEmail())) {
                errors.add("El cliente ya existe con el correo electrónico proporcionado");
            }

            // Check for existing client with same name and last name
            if (clienteService.existByNombreyApellido(body.getNombreApellido())) {
                errors.add("El cliente ya existe con el nombre y apellido proporcionados");
            }

            // If no errors found, proceed with saving the client
            if (errors.isEmpty()) {
                ClienteDto persistedCliente = clienteService.crearCliente(body);
                response.setStatus(HttpStatus.OK);
                response.setData(persistedCliente);
                response.setMessage("Cliente guardado");
                response.setErrors(Collections.emptyList());
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else { // If errors found, return them in the response
                response.setStatus(HttpStatus.BAD_REQUEST);
                response.setData(null);
                response.setMessage("Error al guardar el cliente");
                response.setErrors(errors);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {
            LOGGER.error("Error saving client", e);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage("Error al guardar el cliente");
            response.setErrors(Collections.singletonList("Ocurrió un error inesperado"));
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

