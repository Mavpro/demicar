package org.matias.demicar.controllers;

public class Exceptions {
    public static class ClientAlreadyExistsException extends RuntimeException {

        public ClientAlreadyExistsException(String message) {
            super(message);
        }
    }

    public class InvalidClientDataException extends RuntimeException {

        public InvalidClientDataException(String message) {
            super(message);
        }
    }

}
