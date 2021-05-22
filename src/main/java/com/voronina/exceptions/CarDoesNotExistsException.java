package com.voronina.exceptions;

public class CarDoesNotExistsException extends RuntimeException {

    public CarDoesNotExistsException(String message) {
        super(message);
    }
}
