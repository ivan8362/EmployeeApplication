package com.netcracker.ivanlavrov.myTestTaskEmployee.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class EmployeeAlreadyExistsException extends ResponseStatusException {
    public EmployeeAlreadyExistsException(HttpStatus httpStatus, String errorMessage){
        super(httpStatus, errorMessage);
    }
}
