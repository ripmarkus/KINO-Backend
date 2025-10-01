package com.example.kinoxp.service.employee;

import com.example.kinoxp.model.employee.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    boolean existsById(Integer id);
    Optional<Employee> findById(Integer id);
    List<Employee> findAll();
    Employee save(Employee employee);
    void deleteById(Integer id);
}