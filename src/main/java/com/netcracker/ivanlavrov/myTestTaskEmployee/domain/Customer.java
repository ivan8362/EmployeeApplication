package com.netcracker.ivanlavrov.myTestTaskEmployee.domain;

import lombok.Data;

@Data
//@JsonIgnoreProperties(ignoreUnknown = true) // If there is one field missing, then app will not throw an error.
public class Customer {
    private final String id;
    private final String name;
    private final String description;
    private final String email;
    private final String address;
}
