package com.example.kinoxp.controller;

import com.example.kinoxp.service.customer.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/lookup")
    public ResponseEntity<?> lookupByPhone(@RequestParam String phone) {
        return customerService.findByPhone(phone)
                .<ResponseEntity<?>>map(c -> ResponseEntity.ok(Map.of(
                        "id", c.getCustomerId(),
                        "name", c.getName(),
                        "email", c.getEmail(),
                        "phone", c.getPhone()
                )))
                .orElse(ResponseEntity.status(404).body(Map.of("message", "Not found")));
    }
}
