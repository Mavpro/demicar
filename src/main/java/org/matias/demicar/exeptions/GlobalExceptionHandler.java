package org.matias.demicar.exeptions;

import org.matias.demicar.responses.CustomResponse;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.View;

import java.util.Collections;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final View error;

    public GlobalExceptionHandler(View error) {
        this.error = error;
    }

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
    public ResponseEntity<CustomResponse<Void>> handleGeneralException(ErrorServerExeption ex) {
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

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<CustomResponse<Void>> handleValidationException(DataAccessException ex) {
        CustomResponse<Void> response = new CustomResponse<>(
                HttpStatus.SERVICE_UNAVAILABLE,"Error al accesar a la base de datos",
                Collections.singletonList(ex.getMessage()));
        return new ResponseEntity<>(response, HttpStatus.SERVICE_UNAVAILABLE);
    }

}
