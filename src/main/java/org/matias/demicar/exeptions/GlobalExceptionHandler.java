package org.matias.demicar.exeptions;

import org.matias.demicar.responses.CustomResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<CustomResponse<Void>> handleValidationException(ValidationException ex) {
        CustomResponse<Void> response = new CustomResponse<>(
                HttpStatus.BAD_REQUEST,
                "Errores de validación",
                ex.getErrors()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomResponse<Void>> handleGeneralException(Exception ex) {
        CustomResponse<Void> response = new CustomResponse<>(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Ocurrió un error inesperado",
                Collections.singletonList(ex.getMessage())
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomResponse<Void>> handleEntityNotFoundException(ResourceNotFoundException ex) {
        CustomResponse<Void> response = new CustomResponse<>(
                HttpStatus.NOT_FOUND,
                "Entidad no encontrada",
                Collections.singletonList(ex.getMessage())
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ConstraintsViolation.class)
    public ResponseEntity<CustomResponse<Void>> handleValidationException(ConstraintsViolation ex) {
        CustomResponse<Void> response = new CustomResponse<>(
                HttpStatus.BAD_REQUEST,
                "Errores de validación",
                ex.getErrors()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
