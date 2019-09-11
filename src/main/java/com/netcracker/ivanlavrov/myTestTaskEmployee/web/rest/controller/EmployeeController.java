package com.netcracker.ivanlavrov.myTestTaskEmployee.web.rest.controller;

import com.netcracker.ivanlavrov.myTestTaskEmployee.constants.MessageConstants;
import com.netcracker.ivanlavrov.myTestTaskEmployee.domain.Employee;
import com.netcracker.ivanlavrov.myTestTaskEmployee.repository.EmployeeRepository;
import com.netcracker.ivanlavrov.myTestTask.web.dto.ResponseDTO;
import com.netcracker.ivanlavrov.myTestTaskEmployee.service.EmployeeApi;
import com.netcracker.ivanlavrov.myTestTaskEmployee.web.dto.EmployeeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/employees")
public class EmployeeController {

    @Autowired
    private EmployeeApi employeeService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping(value = "/", produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseDTO addEmployee(@Valid @RequestBody EmployeeDTO employee){
        ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS,
                MessageConstants.EMPLOYEE_ADDED_SUCCESSFULLY);
        employeeService.addEmployee(employee);

        return responseDTO;
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(value = "/", produces = { MediaType.APPLICATION_JSON_VALUE})
    public String showAllEmployees() {
        List<Employee> employees = this.employeeRepository.findAll();
        String html = "[";

        for (Employee employee : employees) {
            html += employee + ",\n";
        }

        html += "]";

        return html;
    }

    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    @PutMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseDTO updateEmployee(
            @Valid @RequestBody EmployeeDTO employeeDTO,
            @PathVariable(name = "id") String id
            ){
        ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS,
                MessageConstants.EMPLOYEE_UPDATED_SUCCESSFULLY);
        employeeService.updateEmployee(
                id, employeeDTO.getDisplayName(), employeeDTO.getCustomerId(), employeeDTO.getFirstName(),
                employeeDTO.getLastName(),
                employeeDTO.getEmail());

        return responseDTO;
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Employee getEmployee(@PathVariable(name = "id") String id){
        return employeeService.getEmployeeById(id);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @DeleteMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseDTO deleteEmployee(@PathVariable(name = "id") String id){
        ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS,
            MessageConstants.EMPLOYEE_DELETED_SUCCESSFULLY);
        employeeService.deleteEmployee(id);

        return responseDTO;
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(value = "/customers/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public String getEmployeesByCustomerId(@PathVariable(name = "id") String id){
        return employeeService.getEmployeesByCustomerId(id);
    }
}
