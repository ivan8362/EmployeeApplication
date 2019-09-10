package com.netcracker.ivanlavrov.myTestTaskEmployee.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * This class is an employee object created by some API consumer, but not from repository.
 * displayName - name of the employee, for example a code John Doe -> JODO.
 * customerId - customer ID. There is a validation in service for this ID.
 * firstName - employee's first name.
 * lastName - employee's last name.
 * email - employee's email address. No validation.
 */

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class EmployeeDTO {

    @NotNull
    @JsonProperty("displayName")
    private String displayName;

    @NotNull
    @JsonProperty("customerId")
    private String customerId;

    @NotNull
    @JsonProperty("firstName")
    private String firstName;

    @NotNull
    @JsonProperty("lastName")
    private String lastName;

    @NotNull
    @JsonProperty("email")
    private String email;

    public EmployeeDTO(@JsonProperty("displayName") String displayName,
                       @JsonProperty("customerId") String customerId,
                       @JsonProperty("firstName") String firstName,
                       @JsonProperty("lastName") String lastName,
                       @JsonProperty("email") String email) {
        this.displayName = displayName;
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}
