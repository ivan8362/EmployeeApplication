package com.netcracker.ivanlavrov.myTestTaskEmployee.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@RequiredArgsConstructor
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

    @Override
    public String toString() {
        return "{" +
                "\"id\" : \"" + id + "\"" +
                ", \"displayName\" : \"" + displayName + "\"" +
                ", \"customerId\" : \"" + customerId + "\"" +
                ", \"firstName\" : \"" + firstName + "\"" +
                ", \"lastName\" : \"" + lastName + "\"" +
                ", \"email\" : \"" + email + "\"" +
                "\n}";
    }
}
