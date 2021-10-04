package com.example.RedisWithSpringBoot.repository;

import com.example.RedisWithSpringBoot.model.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Integer> {

    Employee findById(int id);

}
