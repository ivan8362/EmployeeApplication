package com.netcracker.ivanlavrov.myTestTaskEmployee.service;

import com.netcracker.ivanlavrov.myTestTaskEmployee.domain.Employee;
import com.netcracker.ivanlavrov.myTestTaskEmployee.web.dto.EmployeeDTO;

public interface EmployeeApi {
    /**
     * This method adds an employee to DB.
     * EmployeeDTO is input from API consumer.
     *
     * @param employeeDTO {@link com.netcracker.ivanlavrov.myTestTaskEmployee.web.dto.EmployeeDTO }
     */
    void addEmployee(EmployeeDTO employeeDTO);

    /**
     * This method updates employee in DB. You have to specify some parameters, some can be null.
     * In case customer with such customerId does not exists, API will return an error.
     *
     * @param id - employee ID generated by MongoDB.
     * @param displayName - name of the employee, for example a code. John Doe -> JODO.
     * @param customerId - customer ID. There is a validation in service for this ID.
     * @param firstName - employee's first name.
     * @param lastName - employee's last name.
     * @param email - employee's email address. No validation.
     * @return
     */
    Employee updateEmployee(String id, String displayName, String customerId, String firstName, String lastName, String email);

    /**
     * This method returns employee by the given employeeId
     * @param id - employee ID.
     * @return Employee object.
     */
    Employee getEmployeeById(String id);

    /**
     * This method removes an employee from MongoDB.
     * @param id - employee ID.
     */
    void deleteEmployee(String id);

    /**
     * This method gets all employees from DB which has customerId equals given customerId.
     * @param id - customer ID.
     * @return JSON with employees.
     */
    String getEmployeesByCustomerId(String id);
}