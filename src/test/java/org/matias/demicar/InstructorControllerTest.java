package org.matias.demicar;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.matias.demicar.controllers.InstructorController;
import org.matias.demicar.models.Dtos.InstructorDto;
import org.matias.demicar.responses.CustomResponse;
import org.matias.demicar.services.InstructorServiceI;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InstructorControllerTest {

    // Mockea las dependencias del servicio de instructor
    @Mock
    private InstructorServiceI instructorService;

    // Mockea el resultado de la validación de los datos de entrada
    @Mock
    private BindingResult bindingResult;

    // Inyecta los mocks en el controlador que queremos probar
    @InjectMocks
    private InstructorController instructorController;

    // Un DTO de instructor que se usará en las pruebas
    private InstructorDto instructorDto;

    // Este método se ejecuta antes de cada prueba
    @BeforeEach
    void setUp() {
        // Inicializa el DTO de instructor con datos de prueba
        instructorDto = new InstructorDto();
        instructorDto.setNombre("John");
        instructorDto.setEmail("john@example.com");
    }

    // Prueba para el método findAllInstructors cuando se encuentran instructores
    @Test
    void testFindAllInstructors_NoNombre_InstructorsFound() {
        InstructorDto instructorDto = new InstructorDto();
        instructorDto.setActivo(true);

        // Configura el mock para devolver una lista de instructores
        when(instructorService.getInstructors()).thenReturn(Collections.singletonList(instructorDto));

        // Llama al método del controlador y captura la respuesta
        ResponseEntity<CustomResponse<List<InstructorDto>>> response = instructorController.findAllInstructors(null);

        // Verifica que la respuesta sea correcta
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Instructors encontrados", response.getBody().getMessage());
        assertEquals(1, response.getBody().getData().size());
    }

    // Prueba para el método findAllInstructors cuando no se encuentran instructores
    @Test
    void testFindAllInstructors_NoNombre_NoInstructorsFound() {
        // Configura el mock para devolver una lista vacía
        when(instructorService.getInstructors()).thenReturn(Collections.emptyList());

        // Llama al método del controlador y captura la respuesta
        ResponseEntity<CustomResponse<List<InstructorDto>>> response = instructorController.findAllInstructors(null);

        // Verifica que la respuesta sea correcta
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("No hay instructors para mostrar", response.getBody().getMessage());
        assertEquals(1, response.getBody().getErrors().size());
        assertEquals("No se encontro el instructor", response.getBody().getErrors().get(0));
    }

    // Prueba para el método save cuando los datos del instructor son válidos
    @Test
    void testSave_InstructorValid() {
        // Configura el mock para indicar que no hay errores de validación
        when(bindingResult.hasErrors()).thenReturn(false);
        // Configura el mock para devolver el instructor guardado
        when(instructorService.crearInstructor(instructorDto)).thenReturn(instructorDto);

        // Llama al método del controlador y captura la respuesta
        ResponseEntity<CustomResponse<InstructorDto>> response = instructorController.save(instructorDto, bindingResult);

        // Verifica que la respuesta sea correcta
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Instructor guardado", response.getBody().getMessage());
        assertEquals(instructorDto, response.getBody().getData());
    }

    // Prueba para el método save cuando los datos del instructor son inválidos
    @Test
    void testSave_InstructorInvalid() {
        // Configura el mock para indicar que hay errores de validación
        when(bindingResult.hasErrors()).thenReturn(true);
        // Configura el mock para devolver una lista de errores de campo
        when(bindingResult.getFieldErrors()).thenReturn(Collections.singletonList(new FieldError("instructorDto", "nombre", "Nombre es obligatorio")));

        // Llama al método del controlador y captura la respuesta
        ResponseEntity<CustomResponse<InstructorDto>> response = instructorController.save(instructorDto, bindingResult);

        // Verifica que la respuesta sea correcta
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Errores de validación", response.getBody().getMessage());
        assertEquals(1, response.getBody().getErrors().size());
        assertEquals("nombre: Nombre es obligatorio", response.getBody().getErrors().get(0));
    }

    // Prueba para el método save cuando el email del instructor ya existe
    @Test
    void testSave_InstructorDuplicateEmail() {
        // Configura el mock para indicar que no hay errores de validación
        when(bindingResult.hasErrors()).thenReturn(false);
        // Configura el mock para indicar que el email ya existe
        when(instructorService.existByCorreo(instructorDto.getEmail())).thenReturn(true);

        // Llama al método del controlador y captura la respuesta
        ResponseEntity<CustomResponse<InstructorDto>> response = instructorController.save(instructorDto, bindingResult);

        // Verifica que la respuesta sea correcta
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Errores de validación", response.getBody().getMessage());
        assertEquals(1, response.getBody().getErrors().size());
        assertEquals("El email ya existe", response.getBody().getErrors().get(0));
    }
}

