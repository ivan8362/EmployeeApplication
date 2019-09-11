package com.netcracker.ivanlavrov.myTestTaskEmployee.service.impl;

import com.netcracker.ivanlavrov.myTestTaskEmployee.CustomerClient;
import com.netcracker.ivanlavrov.myTestTaskEmployee.constants.MessageConstants.ErrorMessages;
import com.netcracker.ivanlavrov.myTestTaskEmployee.domain.Employee;
import com.netcracker.ivanlavrov.myTestTaskEmployee.exception.EmployeeManagementException;
import com.netcracker.ivanlavrov.myTestTaskEmployee.repository.EmployeeRepository;
import com.netcracker.ivanlavrov.myTestTaskEmployee.service.EmployeeApi;
import com.netcracker.ivanlavrov.myTestTaskEmployee.web.dto.EmployeeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.netcracker.ivanlavrov.myTestTaskEmployee.exception.EmployeeAlreadyExistsException;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class EmployeeService implements EmployeeApi {
    private static Logger log = Logger.getLogger(CustomerClient.class.getName());

    @Autowired
    private CustomerClient customerClient;

    @Autowired
    private EmployeeRepository employeeRepository;

    public void addEmployee(EmployeeDTO employeeDTO) {
        Employee employee = employeeRepository.findByDisplayName(employeeDTO.getDisplayName());

        if (employee != null) {
            throw new EmployeeAlreadyExistsException(HttpStatus.CONFLICT, ErrorMessages.EMPLOYEE_ALREADY_EXISTS);
        }

        customerClient.checkIfCustomerExists(employeeDTO.getCustomerId());

        Employee newEmployee = new Employee(employeeDTO.getDisplayName(),
                employeeDTO.getCustomerId(),
                employeeDTO.getFirstName(),
                employeeDTO.getLastName(),
                employeeDTO.getEmail()
        );
        employeeRepository.insert(newEmployee);
        log.info("Employee was successfully added in DB.");
    }

    public Employee updateEmployee(String id, String displayName, String customerId, String firstName, String lastName,
                                  String email) {
        Optional<Employee> employee = employeeRepository.findById(id);

        if (!employee.isPresent()) {
            throw new EmployeeManagementException(ErrorMessages.EMPLOYEE_DOES_NOT_EXIST);
        }

        Employee employeeToPersist = employee.get();

        if (!employeeToPersist.getDisplayName().equals(displayName)) {
            employeeToPersist.setDisplayName(displayName);
        }

        if (!employeeToPersist.getCustomerId().equals(customerId)) {
            employeeToPersist.setCustomerId(customerId);
        }

        if (!employeeToPersist.getFirstName().equals(firstName)) {
            employeeToPersist.setFirstName(firstName);
        }

        if (!employeeToPersist.getLastName().equals(lastName)) {
            employeeToPersist.setLastName(lastName);
        }

        if (!employeeToPersist.getEmail().equals(email)) {
            employeeToPersist.setEmail(email);
        }

        log.info("Employee was successfully updated in DB.");

        return employeeRepository.save(employeeToPersist);
    }

    public Employee getEmployeeById(String id){
        Optional<Employee> employee = employeeRepository.findById(id);

        if (!employee.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessages.EMPLOYEE_DOES_NOT_EXIST);
        }

        log.info("Employee was successfully queried by ID.");

        return employee.get();
    }

    public void deleteEmployee(String id){
        Optional<Employee> employee = employeeRepository.findById(id);

        if (employee.isPresent()){
            employeeRepository.deleteById(id);
            log.info("Employee was successfully removed from DB.");
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessages.EMPLOYEE_DOES_NOT_EXIST);
        }
    }

    public String getEmployeesByCustomerId(String customerId){
        List<Employee> employees = this.employeeRepository.findByCustomerId(customerId);
        String html = "[";

        for (Employee employee : employees) {
            html += employee.getId() + ",\n";
        }

        html += "]";
        log.info("Employees were queries by customer ID.");

        return html;
    }
}
