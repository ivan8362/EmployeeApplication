package com.netcracker.dmp.testtask.employee.exceptions;

public class CustomerNotFoundException extends RuntimeException{

    public CustomerNotFoundException(final String message) {
        super(message);
    }
}
