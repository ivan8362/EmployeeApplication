package com.netcracker.dmp.testtask.employee.controllers.dto;

import lombok.Data;

/**
 * This class is a Data Transfer Object which is received as HTTP request body during employee creation or update.
 * displayName - name of the employee, for example a code John Doe -> JODO.
 * customerId - customer ID. There is a validation in services for this ID.
 * firstName - employee's first name.
 * lastName - employee's last name.
 * email - employee's email address. No validation.
 */
@Data
public class EmployeeDTO {
    private String displayName;
    private String customerId;
    private String firstName;
    private String lastName;
    private String email;
}
