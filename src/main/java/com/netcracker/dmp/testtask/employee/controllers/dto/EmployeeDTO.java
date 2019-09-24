package com.netcracker.dmp.testtask.employee.controllers.dto;

import lombok.Data;

/**
 * This class is a Data Transfer Object which is received as HTTP request body during employee creation or update.
 */
@Data
public class EmployeeDTO {
    private String displayName;
    private String customerId;
    private String firstName;
    private String lastName;
    private String email;
}
