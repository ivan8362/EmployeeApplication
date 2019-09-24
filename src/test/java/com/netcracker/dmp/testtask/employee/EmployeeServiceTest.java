package com.netcracker.dmp.testtask.employee;

import com.netcracker.dmp.testtask.employee.clients.CustomerClient;
import com.netcracker.dmp.testtask.employee.config.EmployeeServiceTestConfiguration;
import com.netcracker.dmp.testtask.employee.entities.Employee;
import com.netcracker.dmp.testtask.employee.exceptions.EmployeeAlreadyExistsException;
import com.netcracker.dmp.testtask.employee.exceptions.EmployeeNotFoundException;
import com.netcracker.dmp.testtask.employee.repositories.EmployeeRepository;
import com.netcracker.dmp.testtask.employee.services.impl.EmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {EmployeeServiceTestConfiguration.class})
@TestPropertySource(properties = {"customer.host=127.0.0.1", "customer.port=8080"})
public class EmployeeServiceTest {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CustomerClient customerClient;

    @Test
    public void testCreateEmployee() {
        String displayName = "Test E";
        String customerId = "1";
        String firstName = "Employee first name";
        String lastName = "Employee last name";
        String email = "test@gmail.com";
        doNothing().when(customerClient).checkIfCustomerExists(customerId);

        // act
        Employee employee = employeeService.createEmployee(displayName, customerId, firstName, lastName, email);

        verify(employeeRepository).findByEmail(email);
        verify(customerClient).checkIfCustomerExists(customerId);
        assertEquals("displayName property value does not match an expected value.", displayName, employee.getDisplayName());
        assertEquals("CustomerId property value does not match an expected value.",
                customerId, employee.getCustomerId());
        assertEquals("First Name property value does not match an expected value.", firstName, employee.getFirstName());
        assertEquals("Last Name property value does not match an expected value.", lastName, employee.getLastName());
        assertEquals("First Name property value does not match an expected value.", email, employee.getEmail());
    }

    @Test(expected = EmployeeAlreadyExistsException.class)
    public void createEmployeeExceptionTest() {
        String displayName = "Test E";
        String customerId = "1";
        String firstName = "Employee first name";
        String lastName = "Employee last name";
        String email = "test@gmail.com";
        Employee employee = new Employee(displayName, customerId, firstName, lastName, email);

        Optional<Employee> employeeOptional = Optional.of(employee);
        Mockito.when(employeeRepository.findByEmail(email)).thenReturn(employeeOptional);

        employeeService.createEmployee(displayName, customerId, firstName, lastName, email);
    }

    @Test
    public void getAll_EmployeesFound_ShouldReturnFoundEmployeeEntries() {
        Employee employeeFirst = new Employee("Test E","1",
                "Employee first name", "Employee last name", "test@gmail.com");
        Employee employeeSecond = new Employee("Test E2","2",
                "Employee first name2", "Employee last name2", "test2@gmail.com");

        List<Employee> employees = new ArrayList<>();
        employees.add(employeeFirst);
        employees.add(employeeSecond);

        when(employeeRepository.findAll()).thenReturn(employees);
        List<Employee> allEmployees = employeeService.getAllEmployees();

        verify(employeeRepository).findAll();
        assertArrayEquals("The list of employees given to the method does not match returned list",
                employees.toArray(), allEmployees.toArray());


    }

    @Test
    public void getById_EmployeeEntryFound() {
        // Arrange.
        Employee employee = new Employee("Test E","1",
                "Employee first name", "Employee last name", "test@gmail.com");
        Optional<Employee> optionalEmployee = Optional.of(employee);
        when(employeeRepository.findById("1")).thenReturn(optionalEmployee);

        // Act.
        Employee employeeActual = employeeService.getEmployeeById("1");

        // Assert.
        verify(employeeRepository, times(1)).findById("1");

        assertEquals("The list of employees given to the method does not match returned list",
                employee, employeeActual);
    }

    @Test(expected = EmployeeNotFoundException.class)
    public void getByID_EmployeeEntryNotFoundTest() {
        Optional<Employee> employeeOptional = Optional.empty();
        Mockito.when(employeeRepository.findById("1")).thenReturn(employeeOptional);

        // Act
        employeeService.getEmployeeById("1");
    }

    @Test
    public void updateEmployeeTest() {
        String id = "1";
        String displayName = "Test E";
        String customerId = "1";
        String firstName = "Employee first name";
        String lastName = "Employee last name";
        String email = "test@gmail.com";
        Employee tempEmployee = new Employee(displayName, customerId, firstName, lastName, email);
        tempEmployee.setId(id);
        Optional<Employee> optionalEmployee = Optional.of(tempEmployee);

        Mockito.when(employeeRepository.findById(id)).thenReturn(optionalEmployee);
        Mockito.when(employeeRepository.save(tempEmployee)).thenReturn(tempEmployee);

        // Act
        Employee employee = employeeService.updateEmployee(id, displayName, customerId, firstName, lastName, email);

        verify(employeeRepository).findById(id);
        assertEquals("displayName property value does not match an expected value.", displayName, employee.getDisplayName());
        assertEquals("CustomerId property value does not match an expected value.",
                customerId, employee.getCustomerId());
        assertEquals("First Name property value does not match an expected value.", firstName, employee.getFirstName());
        assertEquals("Last Name property value does not match an expected value.", lastName, employee.getLastName());
        assertEquals("First Name property value does not match an expected value.", email, employee.getEmail());

    }

    @Test(expected = EmployeeNotFoundException.class)
    public void updateEmployeeExceptionTest() {
        String id = "1";
        String displayName = "Test E";
        String customerId = "1";
        String firstName = "Employee first name";
        String lastName = "Employee last name";
        String email = "test@gmail.com";

        Optional<Employee> employeeOptional = Optional.empty();
        Mockito.when(employeeRepository.findById(id)).thenReturn(employeeOptional);

        employeeService.updateEmployee(id, displayName, customerId, firstName, lastName, email);
    }

    @Test
    public void delete_Employee() {
        // Arrange.
        Employee employee = new Employee("Test E","1",
                "Employee first name", "Employee last name", "test@gmail.com");
        employee.setId("1");
        Optional<Employee> optionalEmployee = Optional.of(employee);

        when(employeeRepository.findById("1")).thenReturn(optionalEmployee);

        // Act.
        employeeService.deleteEmployee("1");

        // Assert.
        verify(employeeRepository).deleteById("1");
    }

    @Test
    public void delete_EmployeeByCustomerId() {
        // Arrange.
        Employee employeeFirst = new Employee("Test E","1",
                "Employee first name", "Employee last name", "test@gmail.com");
        employeeFirst.setId("1");
        Optional<Employee> optionalEmployeeFirst = Optional.of(employeeFirst);
        Employee employeeSecond = new Employee("Test E2","1",
                "Employee first name2", "Employee last name2", "test2@gmail.com");
        employeeSecond.setId("2");
        Optional<Employee> optionalEmployeeSecond = Optional.of(employeeSecond);
        List<Employee> employees = new ArrayList<>();
        employees.add(employeeFirst);
        employees.add(employeeSecond);

        when(employeeRepository.findByCustomerId("1")).thenReturn(employees);

        // Act.
        employeeService.deleteEmployeesByCustomerId("1");

        // Assert.
        verify(employeeRepository, times(2)).deleteById(anyString());
    }
}
