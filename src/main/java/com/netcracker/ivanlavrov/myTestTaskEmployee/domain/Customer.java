package com.netcracker.ivanlavrov.myTestTaskEmployee.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/*@Data
@JsonIgnoreProperties(ignoreUnknown = true)*/
public class Customer {
    private String id;
    private String name;
    private String description;
    private String email;
    private String address;
}
