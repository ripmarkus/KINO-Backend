package com.example.kinoxp.repository.employee;

import com.example.kinoxp.model.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepo extends JpaRepository<Employee,Integer> {
    Optional<Employee> findByUsername(String username);
}
