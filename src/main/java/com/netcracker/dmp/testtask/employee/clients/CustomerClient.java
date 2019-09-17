package com.netcracker.dmp.testtask.employee.clients;

import com.netcracker.dmp.testtask.employee.entities.Customer;
import com.netcracker.dmp.testtask.employee.exceptions.CustomerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.logging.Logger;

@Component
public class CustomerClient {
    private static Logger log = Logger.getLogger(CustomerClient.class.getName());

    @Autowired
    private RestTemplate restTemplate;

    @Value("${customer.host}")
    private String host;

    @Value("${customer.port}")
    private Integer port;

    public void checkIfCustomerExists(String customerId) throws CustomerNotFoundException {
        UriComponents uriComponents = UriComponentsBuilder.fromUriString(
                "http://" + host + ":" + port + "/v1/customers/"
                + customerId)
                .build();

        try {
            Customer customer = restTemplate.getForObject(uriComponents.toUri(), Customer.class);
            log.info("A customer was found in DB: " + customer.toString());
        } catch (HttpClientErrorException ex) {
            throw new CustomerNotFoundException("Customer with such customerID is not found.");
        }
    }
}
