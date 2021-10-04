package com.example.RedisWithSpringBoot.controller;

import com.example.RedisWithSpringBoot.model.Employee;
import com.example.RedisWithSpringBoot.repository.EmployeeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    final EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @PostMapping("/createEmployee")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        try {
            Employee emp = employeeRepository.save(employee);
            return new ResponseEntity<>(emp, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/employeeList")
    public ResponseEntity<List<Employee>> getEmployeesList() {
        List<Employee> employeeList = new ArrayList<>();
        employeeRepository.findAll().forEach(employeeList::add);

        return new ResponseEntity<>(employeeList, HttpStatus.OK);
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") int id) {
        Employee employee = employeeRepository.findById(id);

        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @PostMapping("/updateEmployee/{id}")
    public ResponseEntity<Object> getEmployeeById(@PathVariable("id") int id, @RequestBody Employee employee) {
        Employee oldEmp = employeeRepository.findById(id);
        if (Objects.nonNull(oldEmp)) {
            oldEmp.setFirstName(employee.getFirstName());
            oldEmp.setLastName(employee.getLastName());
            oldEmp.setEmail(employee.getEmail());
            oldEmp.setAge(employee.getAge());

            return new ResponseEntity<>(employeeRepository.save(oldEmp), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Employee Not Found with id : " + id, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deleteEmployee/{id}")
    public ResponseEntity<String> deleteEmployeeById(@PathVariable("id") Integer id) {
        employeeRepository.deleteById(id);

        return new ResponseEntity<>("Employee deleted successfully", HttpStatus.OK);
    }
}
