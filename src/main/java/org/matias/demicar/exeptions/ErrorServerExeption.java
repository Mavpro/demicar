package org.matias.demicar.exeptions;

public class ErrorServerExeption extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ErrorServerExeption(String message) {
        super(message);
    }
}
