package org.matias.demicar.controllers;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.matias.demicar.exeptions.ConstraintsViolation;
import org.matias.demicar.exeptions.ResourceNotFoundException;
import org.matias.demicar.exeptions.ValidationException;
import org.matias.demicar.models.Dtos.ClienteDto;
import org.matias.demicar.models.Mappers.ClienteMapperService;
import org.matias.demicar.responses.CustomResponse;
import org.matias.demicar.services.ClienteServiceI;
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
            List<ClienteDto> clientes = new ArrayList<>();
            if (nombre == null) {
                clientes.addAll(clienteService.getClientes().stream().filter(c->c.getActivo()).collect(Collectors.toList()));//no pone el nombre, todos
                response.setStatus(HttpStatus.OK);
                response.setData(clientes);
                response.setMessage("Clientes encontrados con exito.");
                response.setErrors(errores);
                return new ResponseEntity<>(response, HttpStatus.OK);

            } else {
                clienteService.obtenerClientePorNombre(nombre).stream().filter(c->c.getActivo()).forEach(clientes::add);//itera al servicio y a los que coindiden a clientes
                response.setStatus(HttpStatus.OK);
                response.setData(clientes);
                response.setMessage("Clientes encontrado con el nomre : "+ nombre+".");
                response.setErrors(errores);
                return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<CustomResponse<ClienteDto>> save(@Valid @RequestBody ClienteDto body,BindingResult bindingResult) {
        // Verifica si hay errores de validación
        List<String> errors = new ArrayList<>();
        errors = bindingResult.getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());
        if (bindingResult.hasErrors()) {
            throw new ConstraintsViolation(errors);
        } // No se valida en el servicio poirque binding es con el DTO del controller, pero despues si

        ClienteDto persistCliente = clienteService.crearCliente(body);
        CustomResponse<ClienteDto> response = new CustomResponse<>(
                HttpStatus.OK, "Cliente guardado", persistCliente, Collections.emptyList()
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<ClienteDto>> update(@PathVariable Long id, @Valid @RequestBody ClienteDto body,BindingResult bindingResult ) {

        List<String> errors = new ArrayList<>();
        errors = bindingResult.getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());
        if (bindingResult.hasErrors()) {
            throw new ConstraintsViolation(errors);
        } // No se valida en el servicio porque binding es con el DTO del controller, pero despues si


        // Llamar al servicio para actualizar el cliente
        ClienteDto persistCliente = clienteService.actualizarCliente(id, body);

        // Construir la respuesta
        CustomResponse<ClienteDto> response = new CustomResponse<>();
        response.setStatus(HttpStatus.OK);
        response.setData(persistCliente);
        response.setMessage("Cliente actualizado");
        response.setErrors(Collections.emptyList());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<ClienteDto>> delete(@Valid @PathVariable Long id) {
        CustomResponse<ClienteDto> response = new CustomResponse<>();
        List<String> errors = new ArrayList<>();
            try {
                if (clienteService.existById(id)) {
                    clienteService.eliminarCliente(id);
                    response.setStatus(HttpStatus.OK);
                    response.setData(null);
                    response.setMessage("Cliente eliminado");
                    response.setErrors(Collections.emptyList());
                    return new ResponseEntity<>(response, HttpStatus.OK);
                }

                errors.add("El cliente no existe");
                response.setErrors(errors);
                response.setMessage("No se pudo eliminar el cliente");
                response.setData(null);
                response.setStatus(HttpStatus.CONFLICT);
                return new ResponseEntity<>(response, HttpStatus.CONFLICT);

            }catch (Exception e){
                LOGGER.error("Error saving client", e);
                response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
                response.setData(null);
                response.setMessage("Error al eliminar el cliente");
                response.setErrors(Collections.singletonList("Error al eliminar el cliente"));
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);


        }
    }


}