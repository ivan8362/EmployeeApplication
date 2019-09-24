package com.netcracker.dmp.testtask.employee.services.impl;

import com.netcracker.dmp.testtask.employee.repositories.EmployeeRepository;
import com.netcracker.dmp.testtask.employee.clients.CustomerClient;
import com.netcracker.dmp.testtask.employee.entities.Employee;
import com.netcracker.dmp.testtask.employee.exceptions.EmployeeNotFoundException;
import com.netcracker.dmp.testtask.employee.services.EmployeeApi;
import com.netcracker.dmp.testtask.employee.controllers.dto.EmployeeDTO;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.netcracker.dmp.testtask.employee.exceptions.EmployeeAlreadyExistsException;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService implements EmployeeApi {
    private static Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    private CustomerClient customerClient;

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee createEmployee(String displayName, String customerId, String firstName,
                                   String lastName, String email) {
        Optional<Employee> employee = employeeRepository.findByEmail(email);

        if (employee.isPresent()) {
            throw new EmployeeAlreadyExistsException(email);
        }

        customerClient.checkIfCustomerExists(customerId);

        Employee newEmployee = new Employee(displayName, customerId, firstName, lastName, email);
        employeeRepository.insert(newEmployee);
        logger.info("Employee with ID : " + newEmployee.getId() + " was successfully created.");

        return newEmployee;
    }

    public Employee updateEmployee(String id, String displayName, String customerId, String firstName, String lastName,
                                  String email) {
        Optional<Employee> employee = employeeRepository.findById(id);

        if (!employee.isPresent()) {
            throw new EmployeeNotFoundException(id);
        }

        Employee updateEmployee = employee.get();

        if (email != null && !email.trim().isEmpty() && !updateEmployee.getEmail().equals(email)) {
            Optional<Employee> employeeByEmail = employeeRepository.findByEmail(email);

            if (employeeByEmail.isPresent()) {
                throw new EmployeeAlreadyExistsException(id);
            }

            updateEmployee.setEmail(email);
            logger.debug("Employee's email property was updated.");
        }

        if (displayName != null && !displayName.trim().isEmpty() &&
                !updateEmployee.getDisplayName().equals(displayName)) {
            updateEmployee.setDisplayName(displayName);
            logger.debug("Employee's displayName property was updated.");
        }

        if (customerId != null && !customerId.trim().isEmpty()
                && !updateEmployee.getCustomerId().equals(customerId)) {
            updateEmployee.setCustomerId(customerId);
            logger.debug("Employee's customerId property was updated.");
        }

        if (firstName != null && !firstName.trim().isEmpty() && !updateEmployee.getFirstName().equals(firstName)) {
            updateEmployee.setFirstName(firstName);
            logger.debug("Employee's firstName property was updated.");
        }

        if (lastName != null && !lastName.trim().isEmpty() && !updateEmployee.getLastName().equals(lastName)) {
            updateEmployee.setLastName(lastName);
            logger.debug("Employee's lastName property was updated.");
        }

        Employee returnEmployee = employeeRepository.save(updateEmployee);
        logger.info("Employee with ID: " + id + " was successfully updated.");

        return returnEmployee;
    }

    public Employee getEmployeeById(String id){
        Optional<Employee> employee = employeeRepository.findById(id);

        if (!employee.isPresent()){
            throw new EmployeeNotFoundException(id);
        }

        return employee.get();
    }

    public void deleteEmployee(String id){
        Employee employee = getEmployeeById(id);

        employeeRepository.deleteById(id);
        logger.info("Employee with ID: " + id + " was successfully removed.");
    }

    public void deleteEmployeesByCustomerId(String customerId){
        List<Employee> employees = this.employeeRepository.findByCustomerId(customerId);
        logger.debug("Employees were queried by customer ID.");

        if (employees.isEmpty()) {
            logger.info("No employees deleted for the customer with ID: " + customerId);

            return;
        }

        for (Employee employee : employees) {
            employeeRepository.deleteById(employee.getId());
            logger.info("Employee with id " + employee.getId() + " was deleted.");
        }
    }

    public List<Employee> getAllEmployees() {
        List<Employee> employees = this.employeeRepository.findAll();

        return employees;
    }
}
