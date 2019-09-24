package com.netcracker.dmp.testtask.employee.controllers;

import com.netcracker.dmp.testtask.employee.entities.Employee;
import com.netcracker.dmp.testtask.employee.services.EmployeeApi;
import com.netcracker.dmp.testtask.employee.controllers.dto.EmployeeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("v1/employees")
public class EmployeeController {
    private static Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeApi employeeService;

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping(value = "/", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE},
            consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
    public Employee createEmployee(@RequestBody EmployeeDTO employeeDTO){
        logger.debug("A client called POST /v1/employees/ REST API with parameters: " + employeeDTO);

        Employee employee = employeeService.createEmployee(employeeDTO.getDisplayName(), employeeDTO.getCustomerId(),
                employeeDTO.getFirstName(), employeeDTO.getLastName(), employeeDTO.getEmail());
        logger.debug("Employee with name: " + employee.getDisplayName()
                + " and ID: " + employee.getId() + " was successfully created.");

        return employee;
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(value = "/", produces = { MediaType.APPLICATION_JSON_VALUE})
    public List<Employee> getAllEmployees() {
        logger.debug("A client called GET /v1/employees/ REST API with no parameters.");

        return employeeService.getAllEmployees();
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PutMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = { MediaType.APPLICATION_JSON_VALUE })
    public Employee updateEmployee(
            @RequestBody EmployeeDTO employeeDTO,
            @PathVariable(name = "id") String id
            ){
        logger.debug("A client called PUT /v1/employees/"
                + id + "/ REST API with parameters: " + employeeDTO);

        Employee employee = employeeService.updateEmployee(
                id, employeeDTO.getDisplayName(), employeeDTO.getCustomerId(), employeeDTO.getFirstName(),
                employeeDTO.getLastName(),
                employeeDTO.getEmail());
        logger.debug("Employee with ID: " + id + " was successfully updated.");

        return employee;
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Employee getEmployee(@PathVariable(name = "id") String id){
        logger.debug("A client called GET /v1/employees/" + id + "/ REST API with no parameters.");

        return employeeService.getEmployeeById(id);
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void deleteEmployee(@PathVariable(name = "id") String id){
        logger.debug("A client called DELETE /v1/employees/" + id + "/ REST API with no parameters.");

        employeeService.deleteEmployee(id);
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/customers/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void deleteEmployeesByCustomerId(@PathVariable(name = "id") String id){
        logger.debug("A client called DELETE /v1/employees/customers/"
                + id + "/ REST API with no parameters.");

        employeeService.deleteEmployeesByCustomerId(id);
    }
}
