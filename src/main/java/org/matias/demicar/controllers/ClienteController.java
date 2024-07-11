package org.matias.demicar.controllers;

import org.matias.demicar.models.Dtos.ClienteDto;
import org.matias.demicar.models.Mappers.ClienteMapperService;
import org.matias.demicar.models.entities.Cliente;
import org.matias.demicar.responses.CustomResponse;
import org.matias.demicar.services.ClienteServiceI;
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
    @Autowired
    private DefaultSslBundleRegistry sslBundleRegistry;

    @GetMapping
    public ResponseEntity<CustomResponse<List<ClienteDto>>> findAllClientes(@RequestParam(required = false) String nombre) {
        List<String> errores = new ArrayList<>();
        CustomResponse<List<ClienteDto>> response = new CustomResponse<>();
        try {
            List<ClienteDto> clientes = new ArrayList<>();
            if (nombre == null) {
                clientes.addAll(clienteService.getClientes());//no pone el nombre, todos
            }else{
                clienteService.obtenerClientePorNombre(nombre).forEach(clientes::add);//itera al servicio y a los que coindiden a clientes
            }
            if (clientes.isEmpty() || clientes.size() == 0) { // VACIO ?
                errores.add("No se encontro el cliente");
                response.setData(null);
                response.setMessage("No hay clientes para mostrar");
                response.setErrors(errores);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

            }
            else {
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
        try {
            if(clienteService.existByNombreyApellido(body.getNombreApellido())){
                response.setStatus(HttpStatus.CONFLICT);
                response.setMessage("El cliente ya existe");
                response.setErrors(Collections.singletonList("El cliente ya existe"));
                response.setData(null);
                return new ResponseEntity<>(response, HttpStatus.CONFLICT);
            }
            if(clienteService.existByCorreo(body.getEmail())){
                response.setStatus(HttpStatus.CONFLICT);
                response.setMessage("El cliente ya existe");
                response.setErrors(Collections.singletonList("El cliente ya existe"));
                response.setData(null);
                return new ResponseEntity<>(response, HttpStatus.CONFLICT);
            }

            ClienteDto persistCliente = clienteService.crearCliente(body);
            response.setStatus(HttpStatus.OK);
            response.setData(persistCliente);
            response.setMessage("Cliente guardado");
            response.setErrors(Collections.emptyList());
            return new ResponseEntity<>(response, HttpStatus.OK);

        }catch (Exception e) {
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.setData(null);
            response.setMessage("NO se que paso");
            response.setErrors(Collections.singletonList("Anda a saber"));
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }



    }




}

