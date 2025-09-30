package com.example.kinoxp.repository;

import com.example.kinoxp.model.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepo extends JpaRepository<Customer,Integer> {
}
