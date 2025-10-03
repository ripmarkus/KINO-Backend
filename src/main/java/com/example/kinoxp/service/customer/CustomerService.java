package com.example.kinoxp.service.customer;

import com.example.kinoxp.model.customer.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    boolean existsById(Integer id);
    Optional<Customer> findById(Integer id);
    List<Customer> findAll();
    Customer save(Customer customer);
    void deleteById(Integer id);
    Customer getRequiredCustomer(Integer id);
}
