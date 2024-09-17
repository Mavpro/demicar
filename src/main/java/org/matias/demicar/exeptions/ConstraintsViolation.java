package org.matias.demicar.exeptions;

import java.util.List;

public class ConstraintsViolation extends RuntimeException {
    private List<String> errors;



    public ConstraintsViolation(List<String> errors) {
        super("Errores de validación");
        this.errors = errors;
    }
    public List<String> getErrors() {
        return errors;
    }

}
