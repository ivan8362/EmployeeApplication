package com.netcracker.dmp.testtask.employee.services.impl;

import com.netcracker.dmp.testtask.employee.repositories.EmployeeRepository;
import com.netcracker.dmp.testtask.employee.clients.CustomerClient;
import com.netcracker.dmp.testtask.employee.constants.MessageConstants.ErrorMessages;
import com.netcracker.dmp.testtask.employee.entities.Employee;
import com.netcracker.dmp.testtask.employee.exceptions.EmployeeNotFoundException;
import com.netcracker.dmp.testtask.employee.services.EmployeeApi;
import com.netcracker.dmp.testtask.employee.controllers.dto.EmployeeDTO;
import org.slf4j.event.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.netcracker.dmp.testtask.employee.exceptions.EmployeeAlreadyExistsException;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import static jdk.nashorn.internal.objects.NativeMath.log;

@Service
public class EmployeeService implements EmployeeApi {
    private static Logger log = Logger.getLogger(EmployeeService.class.getName());

    @Autowired
    private CustomerClient customerClient;

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee createEmployee(String displayName, String customerId, String firstName,
                                   String lastName, String email) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setDisplayName(displayName);
        employeeDTO.setCustomerId(customerId);
        employeeDTO.setFirstName(firstName);
        employeeDTO.setLastName(lastName);
        employeeDTO.setEmail(email);
        Optional<Employee> employee = employeeRepository.findByEmail(employeeDTO.getEmail());

        if (employee.isPresent()) {
            throw new EmployeeAlreadyExistsException(email);
        }

        customerClient.checkIfCustomerExists(employeeDTO.getCustomerId());

        Employee newEmployee = new Employee(employeeDTO.getDisplayName(),
                employeeDTO.getCustomerId(),
                employeeDTO.getFirstName(),
                employeeDTO.getLastName(),
                employeeDTO.getEmail()
        );
        employeeRepository.insert(newEmployee);
        log.info("Employee with ID : " + newEmployee.getId() + " was successfully created.");

        return newEmployee;
    }

    public Employee updateEmployee(String id, String displayName, String customerId, String firstName, String lastName,
                                  String email) {
        Optional<Employee> employee = employeeRepository.findById(id);

        if (!employee.isPresent()) {
            throw new EmployeeNotFoundException(id);
        }

        Optional<Employee> employeeByEmail = employeeRepository.findByEmail(email);

        if (employeeByEmail.isPresent()) {
            throw new EmployeeAlreadyExistsException(id);
        }

        Employee updateEmployee = employee.get();

        if (displayName != null && !displayName.trim().isEmpty() &&
                !updateEmployee.getDisplayName().equals(displayName)) {
            updateEmployee.setDisplayName(displayName);
            log(Level.DEBUG,"Employee's displayName property was updated.");
        }

        if (customerId != null && !customerId.trim().isEmpty()
                && !updateEmployee.getCustomerId().equals(customerId)) {
            updateEmployee.setCustomerId(customerId);
            log(Level.DEBUG,"Employee's customerId property was updated.");
        }

        if (firstName != null && !firstName.trim().isEmpty() && !updateEmployee.getFirstName().equals(firstName)) {
            updateEmployee.setFirstName(firstName);
            log(Level.DEBUG,"Employee's firstName property was updated.");
        }

        if (lastName != null && !lastName.trim().isEmpty() && !updateEmployee.getLastName().equals(lastName)) {
            updateEmployee.setLastName(lastName);
            log(Level.DEBUG, "Employee's lastName property was updated.");
        }

        if (email != null && !email.trim().isEmpty() && !updateEmployee.getEmail().equals(email)) {
            updateEmployee.setEmail(email);
            log(Level.DEBUG,"Employee's email property was updated.");
        }

        Employee returnEmployee = employeeRepository.save(updateEmployee);
        log.info("Employee with ID: " + id + " was successfully updated.");

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

        if (employee != null){
            employeeRepository.deleteById(id);
            log.info("Employee with ID: " + id + " was successfully removed.");
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessages.EMPLOYEE_DOES_NOT_EXIST);
        }
    }

    public void deleteEmployeesByCustomerId(String customerId){
        List<Employee> employees = this.employeeRepository.findByCustomerId(customerId);
        log(Level.DEBUG, "Employees were queried by customer ID.");

        if (employees.isEmpty()) {
            log.info("No employees deleted for the customer with ID: " + customerId);

            return;
        }

        for (Employee employee : employees) {
            employeeRepository.deleteById(employee.getId());
            log.info("Employee with id " + employee.getId() + " was deleted.");
        }
    }

    public List<Employee> getAllEmployees() {
        List<Employee> employees = this.employeeRepository.findAll();

        return employees;
    }
}
