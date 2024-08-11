package org.matias.demicar.controllers;

import jakarta.validation.Valid;
import org.matias.demicar.models.Dtos.ClienteDto;
import org.matias.demicar.models.Dtos.SolicitudDeAgendaDto;
import org.matias.demicar.models.Mappers.ClienteMapperService;
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
/*

        @PostMapping
        public ResponseEntity<SolicitudDeAgendaDto> save(@RequestBody SolicitudDeAgendaDto solicitud,BindingResult bindingResult) {
            CustomResponse<ClienteController> response = new CustomResponse<>();
            List<String> errors = new ArrayList<>();
            if(bindingResult.hasErrors()) {
                errors.addAll(bindingResult.getFieldErrors().stream()
                        .map(error->error.getField()+":"+error.getDefaultMessage())
                        .collect(Collectors.toList()));
            }//si hay algun error me da compo y error

            if (solicitudDeAgendaService.existById())
            return asd;
        }

        @GetMapping("/{id}")
        public ResponseEntity<Solicitud> obtenerSolicitudPorId(@PathVariable Long id) {
            return solicitudService.obtenerSolicitudPorId(id)
                    .map(solicitud -> ResponseEntity.ok(solicitud))
                    .orElse(ResponseEntity.notFound().build());
        }

        // ... otros métodos, como actualizar, eliminar, etc.

        @GetMapping("/cliente/{clienteId}")
        public List<Solicitud> obtenerSolicitudesPorCliente(@PathVariable Long clienteId) {
            return solicitudService.obtenerSolicitudesPorCliente(clienteId);
        }

        @GetMapping("/cliente/{clienteId}/rango/{fechaInicio}/{fechaFin}")
        public List<Solicitud> obtenerSolicitudesPorClienteYRangoFechas(
                @PathVariable Long clienteId,
                @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
                @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin)  
        {
            return solicitudService.obtenerSolicitudesPorClienteYRangoFechas(clienteId, fechaInicio, fechaFin);
        }*/
    }