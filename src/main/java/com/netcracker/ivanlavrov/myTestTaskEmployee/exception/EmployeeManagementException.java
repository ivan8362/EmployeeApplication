package com.netcracker.ivanlavrov.myTestTaskEmployee.exception;

public class EmployeeManagementException extends RuntimeException {
    private static final long serialVersionUID = 4269316494280751012L;

    public EmployeeManagementException(final String message) {
        super(message);
    }

    public EmployeeManagementException(final String message, final Throwable throwable) {
        super(message, throwable);
    }
}
