package com.netcracker.dmp.testtask.employee.services;

import com.netcracker.dmp.testtask.employee.entities.Employee;

import java.util.List;

public interface EmployeeApi {
    /**
     * This method adds an employee to persistence level.
     * You have to specify some parameters, some can be left unchanged.
     * In case employee with such employeeId does not exists, API will return an error.
     * Employee's properties are input from API consumer.
     *
     * @param displayName name of the employee, for example a code. John Doe -> JODO.
     * @param customerId customer ID. There is a validation in services for this ID.
     * @param firstName employee's first name.
     * @param lastName employee's last name.
     * @param email employee's email address. No validation.
     * @return Employee object with ID and all other parameters.
     */
    Employee createEmployee(String displayName, String customerId, String firstName, String lastName, String email);

    /**
     * This method updates employee in persistence level. You have to specify some parameters,
     * some can be left unchanged.
     * In case customer with such customerId does not exists, API will return an error.
     *
     * @param id - employee ID.
     * @param displayName - name of the employee, for example a code. John Doe -> JODO.
     * @param customerId - customer ID. There is a validation in services for this ID.
     * @param firstName - employee's first name.
     * @param lastName - employee's last name.
     * @param email - employee's email address. No validation.
     * @return employee object with ID and all other parameters.
     */
    Employee updateEmployee(String id, String displayName, String customerId, String firstName, String lastName, String email);

    /**
     * This method returns employee by the given employeeId
     * @param id - employee ID.
     * @return Employee object.
     */
    Employee getEmployeeById(String id);

    /**
     * This method removes an employee from persistence level.
     * @param id - employee ID.
     */
    void deleteEmployee(String id);

    /**
     * This method deletes all employees from persistence level which has customerId equals given customerId.
     * @param id - customer ID.
     * @return void.
     */
    void deleteEmployeesByCustomerId(String id);

    /**
     * This method returns list of all employees with all the details.
     * @return List<Employee>.
     */
    List<Employee> getAllEmployees();
}
