package com.netcracker.dmp.testtask.employee.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "employee")
public class Employee {

    @Id
    private String id;

    private String displayName;
    private String customerId;
    private String firstName;
    private String lastName;
    private String email;

    public Employee(String displayName, String customerId, String firstName, String lastName, String email) {
        this.displayName = displayName;
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}
