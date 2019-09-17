package com.netcracker.dmp.testtask.employee.repositories;

import com.netcracker.dmp.testtask.employee.entities.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;


public interface EmployeeRepository extends MongoRepository<Employee, String> {

    @Query("{ 'email' : {$regex: ?0, $options: 'i' }}")
    Optional<Employee> findByEmail(final String email);

    @Query("{ 'customerId' : {$regex: ?0, $options: 'i' }}")
    List<Employee> findByCustomerId(final String customerId);
}
