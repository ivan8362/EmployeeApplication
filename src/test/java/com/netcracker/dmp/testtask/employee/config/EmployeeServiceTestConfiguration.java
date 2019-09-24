package com.netcracker.dmp.testtask.employee.config;

import com.netcracker.dmp.testtask.employee.clients.CustomerClient;
import com.netcracker.dmp.testtask.employee.repositories.EmployeeRepository;
import com.netcracker.dmp.testtask.employee.services.impl.EmployeeService;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@TestConfiguration
public class EmployeeServiceTestConfiguration {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    public CustomerClient customerClient(){
        return Mockito.mock(CustomerClient.class);
    }

    @Bean
    public EmployeeRepository employeeRepository(){
        return Mockito.mock(EmployeeRepository.class);
    }

    @Bean
    public EmployeeService employeeService() {
        return new EmployeeService();
    }
}
