package org.matias.demicar.validator;

import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BindingValidator {
    public List<String> ValidaCampos (BindingResult result) {
        List<String> errors = new ArrayList<String>();
        if (result.hasErrors()) {

            errors = result.getFieldErrors().stream().map(error -> error.getField() + ": " + error.getDefaultMessage()).collect(Collectors.toList());

        }
    return errors;

    }
}

