package com.example.kinoxp.repository.customer;

import com.example.kinoxp.model.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepo extends JpaRepository<Customer,Integer> {
  Optional<Customer> findByPhone(String phone);
}
