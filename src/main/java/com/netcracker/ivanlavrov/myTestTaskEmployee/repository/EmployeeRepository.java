package com.netcracker.ivanlavrov.myTestTaskEmployee.repository;

import com.netcracker.ivanlavrov.myTestTaskEmployee.domain.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;


public interface EmployeeRepository extends MongoRepository<Employee, String> {

    @Query("{ 'name' : {$regex: ?0, $options: 'i' }}")
    Employee findByDisplayName(final String displayName);

    @Query( "{ '_id' : {}}" )
    void updateEmployee(final Employee employee, String id);

}
