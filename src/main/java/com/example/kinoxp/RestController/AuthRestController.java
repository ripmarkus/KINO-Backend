package com.example.kinoxp.RestController;

import com.example.kinoxp.model.employee.Employee;
import com.example.kinoxp.repository.employee.EmployeeRepo;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthRestController {

    private final EmployeeRepo employeeRepo;

    public AuthRestController(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    // DTO klasser til login request og response
    public static class LoginRequest {
        public String username;
        public String password;
    }

    public static class LoginResponse {
        public String message;
        public boolean success;
        public String role;

        public LoginResponse(String message, boolean success, String role) {
            this.message = message;
            this.success = success;
            this.role = role;
        }
    }

    @PostMapping("/login") // Endpoint for employee login
    public LoginResponse login(@RequestBody LoginRequest loginRequest) { // Får login-request
        Optional<Employee> optionalEmployee = employeeRepo.findByUsername(loginRequest.username); // Check om employee eksisterer

        if (optionalEmployee.isEmpty()) {
        return new LoginResponse("User not found", false, null); // Hvis ikke, returner fejl
        }

        Employee employee = optionalEmployee.get();

        if (!employee.getPassword().equals(loginRequest.password)) { // Check om password matcher
            return new LoginResponse("Invalid password", false, null); // Hvis ikke, returner fejl
        }

        String role = employee.getRoles().stream()
                .findFirst()
                .map(Enum::name)
                .orElse("SALES_CLERK"); // Hent rolle

        return new LoginResponse("Login successful as " + role, true, role); // Returner succes
    }
}
