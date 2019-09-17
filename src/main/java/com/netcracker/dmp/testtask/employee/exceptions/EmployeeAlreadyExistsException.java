package com.netcracker.dmp.testtask.employee.exceptions;

public class EmployeeAlreadyExistsException extends RuntimeException {
    public EmployeeAlreadyExistsException(String email){
        super("Employee with email \"" + email + "\" already exists.");
    }
}
