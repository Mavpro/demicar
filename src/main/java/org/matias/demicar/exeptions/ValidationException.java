package org.matias.demicar.exeptions;

import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;

import java.util.List;

public class ValidationException extends RuntimeException {
    private List<String> errors;



    public ValidationException(List<String> errors) {
        super("Errores de validación");
        this.errors = errors;
    }
    public List<String> getErrors() {
        return errors;
    }





}