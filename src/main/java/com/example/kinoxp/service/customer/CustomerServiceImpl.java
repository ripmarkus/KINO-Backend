package com.example.kinoxp.service.customer;

import com.example.kinoxp.exception.EntityNotFoundException;
import com.example.kinoxp.model.customer.Customer;
import com.example.kinoxp.repository.customer.CustomerRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    
    private final CustomerRepo customerRepo;
    
    public CustomerServiceImpl(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    public Customer getRequiredCustomer(Integer id) {
        return customerRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer", id));
    }

    @Override
    public boolean existsById(Integer id) {
        return customerRepo.existsById(id);
    }
    
    @Override
    public Optional<Customer> findById(Integer id) {
        return customerRepo.findById(id);
    }
    
    @Override
    public List<Customer> findAll() {
        return customerRepo.findAll();
    }
    
    @Override
    public Customer save(Customer customer) {
        return customerRepo.save(customer);
    }
    
    @Override
    public void deleteById(Integer id) {
        customerRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer", id));
        customerRepo.deleteById(id);
    }
}
