package com.example.kinoxp.service.employee;

import com.example.kinoxp.model.employee.Employee;
import com.example.kinoxp.repository.employee.EmployeeRepo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    
    private final EmployeeRepo employeeRepo;
    
    public EmployeeServiceImpl(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    @Override
    public boolean existsById(Integer id) {
        return employeeRepo.existsById(id);
    }
    
    @Override
    public Optional<Employee> findById(Integer id) {
        return employeeRepo.findById(id);
    }
    
    @Override
    public List<Employee> findAll() {
        return employeeRepo.findAll();
    }
    
    @Override
    public Employee save(Employee employee) {
        return employeeRepo.save(employee);
    }
    
    @Override
    public void deleteById(Integer id) {
        employeeRepo.deleteById(id);
    }
    
    @Override
    public Employee getRequiredEmployee(Integer id) {
        return employeeRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"));
    }
}
