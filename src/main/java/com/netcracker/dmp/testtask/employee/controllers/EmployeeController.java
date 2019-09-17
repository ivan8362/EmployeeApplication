package com.netcracker.dmp.testtask.employee.controllers;

import com.netcracker.dmp.testtask.employee.entities.Employee;
import com.netcracker.dmp.testtask.employee.services.EmployeeApi;
import com.netcracker.dmp.testtask.employee.controllers.dto.EmployeeDTO;
import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

import static jdk.nashorn.internal.objects.NativeMath.log;

@RestController
@RequestMapping("v1/employees")
public class EmployeeController {
    private static Logger log = Logger.getLogger(EmployeeController.class.getName());

    @Autowired
    private EmployeeApi employeeService;

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping(value = "/", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE},
            consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
    public Employee createEmployee(@RequestBody EmployeeDTO employeeDTO){
        log(Level.DEBUG, "A client called POST /v1/employees/ REST API with parameters: " + employeeDTO);

        Employee employee = employeeService.createEmployee(employeeDTO.getDisplayName(), employeeDTO.getCustomerId(),
                employeeDTO.getFirstName(), employeeDTO.getLastName(), employeeDTO.getEmail());
        log(Level.DEBUG, "Employee with ID: " + employee.getId() + " was successfully added to DB.");

        return employee;
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(value = "/", produces = { MediaType.APPLICATION_JSON_VALUE})
    public List<Employee> getAllEmployees() {
        log(Level.DEBUG, "A client called GET /v1/employees/ REST API with no parameters.");

        return employeeService.getAllEmployees();
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PutMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = { MediaType.APPLICATION_JSON_VALUE })
    public Employee updateEmployee(
            @RequestBody EmployeeDTO employeeDTO,
            @PathVariable(name = "id") String id
            ){
        log(Level.DEBUG, "A client called PUT /v1/employees/" + id + "/ REST API with parameters: " + employeeDTO);

        Employee employee = employeeService.updateEmployee(
                id, employeeDTO.getDisplayName(), employeeDTO.getCustomerId(), employeeDTO.getFirstName(),
                employeeDTO.getLastName(),
                employeeDTO.getEmail());

        return employee;
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Employee getEmployee(@PathVariable(name = "id") String id){
        log(Level.DEBUG, "A client called GET /v1/employees/" + id + "/ REST API with no parameters.");

        return employeeService.getEmployeeById(id);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @DeleteMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void deleteEmployee(@PathVariable(name = "id") String id){
        log(Level.DEBUG, "A client called DELETE /v1/employees/" + id + "/ REST API with no parameters.");

        employeeService.deleteEmployee(id);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @DeleteMapping(value = "/customers/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void deleteEmployeesByCustomerId(@PathVariable(name = "id") String id){
        log(Level.DEBUG, "A client called DELETE /v1/employees/customers/" + id + "/ REST API with no parameters.");

        employeeService.deleteEmployeesByCustomerId(id);
    }
}
