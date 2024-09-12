package org.matias.demicar.controllers;

import jakarta.validation.Valid;
import org.matias.demicar.models.Dtos.AutoDto;
import org.matias.demicar.models.Dtos.ClienteDto;
import org.matias.demicar.models.Dtos.SolicitudDeAgendaDto;
import org.matias.demicar.models.Mappers.ClienteMapperService;
import org.matias.demicar.models.entities.SolicitudDeAgenda;
import org.matias.demicar.responses.CustomResponse;
import org.matias.demicar.services.ClienteServiceI;
import org.matias.demicar.services.SolicitudDeAgendaServiceI;
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
@RequestMapping("/api/solicitudes")
public class SolicitudesDeAgendaController {

    @Autowired
    ClienteServiceI clienteService;
    @Autowired
    ClienteMapperService clienteMapper;
    @Autowired
    SolicitudDeAgendaServiceI solicitudDeAgendaService;
    private static final Logger LOGGER = LoggerFactory.getLogger(SolicitudesDeAgendaController.class);

    @PostMapping
    public ResponseEntity<CustomResponse<SolicitudDeAgendaDto>> save(@Valid @RequestBody SolicitudDeAgendaDto body, BindingResult bindingResult) {
        CustomResponse<SolicitudDeAgendaDto> response = new CustomResponse<>();
        List<String> errors = new ArrayList<>();

        if (bindingResult.hasErrors()) {
            errors.addAll(bindingResult.getFieldErrors().stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.toList()));        }

        try {
            SolicitudDeAgendaDto savedSolicitud = solicitudDeAgendaService.crearSolicitudDeAgenda(body);
            System.out.println(savedSolicitud);
            response.setData(savedSolicitud);
            response.setErrors(Collections.emptyList());
            response.setStatus(HttpStatus.CREATED);
            response.setMessage("Solicitud de agenda agregada con exito");
            response.setData(savedSolicitud);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            errors.add(e.getMessage()+e.getCause());
            response.setErrors(errors.stream().toList());
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.setMessage(e.getMessage() + " Solicitud no guardada");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping
    public ResponseEntity<CustomResponse<List<SolicitudDeAgendaDto>>> findSolicitudes( ) {
        List<String> errores = new ArrayList<>();
        CustomResponse<List<SolicitudDeAgendaDto>> response = new CustomResponse<>();
        try {
            List<SolicitudDeAgendaDto> solics = new ArrayList<>();
            solics=solicitudDeAgendaService.getSolicitudDeAgendas();

            if (solics.isEmpty() ) { // VACIO ?
                errores.add("No hay solicitud de agenda");
                response.setData(null);
                response.setMessage("No hay solicitudes para mostrar");
                response.setErrors(errores);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

            } else {
                response.setStatus(HttpStatus.OK);
                response.setData(solics);
                response.setMessage("Solicitudes encontradas");
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

    }