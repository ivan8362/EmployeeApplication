package com.netcracker.dmp.testtask.employee.exceptions;

public class EmployeeNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 4269316494280751012L;

    public EmployeeNotFoundException(String employeeID) {
        super("Could not find employee with id " + employeeID);
    }
}