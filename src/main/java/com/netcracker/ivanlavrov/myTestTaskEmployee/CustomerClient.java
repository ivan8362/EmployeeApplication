package com.netcracker.ivanlavrov.myTestTaskEmployee;

import com.netcracker.ivanlavrov.myTestTaskEmployee.domain.Customer;
import com.netcracker.ivanlavrov.myTestTaskEmployee.exception.CustomerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

public class CustomerClient {

    @Autowired
    private RestTemplate restTemplate;

    public void checkIfCustomerExists(String customerId) throws CustomerNotFoundException {
        Customer customer = restTemplate.getForObject(
                "127.0.0.1:8080/rest/api/v1/customers/" + customerId,
                Customer.class);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer with such customerID is not found.");
        }
    }
}
