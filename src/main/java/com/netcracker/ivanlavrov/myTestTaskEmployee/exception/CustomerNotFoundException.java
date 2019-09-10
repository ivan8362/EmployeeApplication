package com.netcracker.ivanlavrov.myTestTaskEmployee.exception;

public class CustomerNotFoundException extends RuntimeException{

    public CustomerNotFoundException(final String message) {
        super(message);
    }

    public CustomerNotFoundException(final String message, final Throwable throwable) {
        super(message, throwable);
    }
}
