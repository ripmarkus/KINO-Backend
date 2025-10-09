package com.example.kinoxp.service.customer;

import com.example.kinoxp.model.customer.Customer;
import com.example.kinoxp.repository.customer.CustomerRepo;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    
    private final CustomerRepo customerRepo;
    
    public CustomerServiceImpl(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    static String normalizePhone(String raw) {
        if (raw == null) return null;
        String trimmed = raw.trim();
        // behold + og tal; fjern alt andet
        String only = trimmed.replaceAll("[^+\\d]", "");
        // (valgfrit) sikre ét + i starten
        if (only.startsWith("00")) { // konverter evt. 00 til +
            only = "+" + only.substring(2);
        }
        return only;
    }

    private static boolean hasText(String s) {
        return StringUtils.hasText(s);
    }

    public Customer getRequiredCustomer(Integer id) {
        return customerRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
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
        customerRepo.deleteById(id);
    }

    @Override
    public Optional<Customer> findByPhone(String rawPhone) {
        String phone = normalizePhone(rawPhone);
        if (!hasText(phone)) return Optional.empty();
        return customerRepo.findByPhone(phone);
    }

    @Override
    @Transactional
    public Customer getOrCreateByPhone(String rawPhone, String name, String email) {
        String phone = normalizePhone(rawPhone);
        if (!hasText(phone)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Phone is required");
        }

        return customerRepo.findByPhone(phone)
                .map(existing -> maybePatchNameEmail(existing, name, email))
                .orElseGet(() -> {
                    Customer c = new Customer();
                    c.setPhone(phone);
                    if (hasText(name))  c.setName(name.trim());
                    if (hasText(email)) c.setEmail(email.trim());
                    try {
                        return customerRepo.save(c);
                    } catch (DataIntegrityViolationException e) {
                        // race condition: en anden request oprettede kunden parallelt
                        return customerRepo.findByPhone(phone)
                                .map(found -> maybePatchNameEmail(found, name, email))
                                .orElseThrow(() -> e);
                    }
                });
    }

    @Override
    @Transactional
    public Customer upsertByPhone(String rawPhone, String name, String email) {
        String phone = normalizePhone(rawPhone);
        if (!hasText(phone)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Phone is required");
        }

        return customerRepo.findByPhone(phone)
                .map(existing -> maybePatchNameEmail(existing, name, email))
                .orElseGet(() -> {
                    Customer c = new Customer();
                    c.setPhone(phone);
                    if (hasText(name))  c.setName(name.trim());
                    if (hasText(email)) c.setEmail(email.trim());
                    try {
                        return customerRepo.save(c);
                    } catch (DataIntegrityViolationException e) {
                        // race: find igen og patch
                        return customerRepo.findByPhone(phone)
                                .map(found -> maybePatchNameEmail(found, name, email))
                                .orElseThrow(() -> e);
                    }
                });
    }

    /** Patch name/email hvis mangler eller er anderledes og vi har nye non-blank værdier. */
    private Customer maybePatchNameEmail(Customer existing, String name, String email) {
        boolean changed = false;
        if (hasText(name) && !name.trim().equals(existing.getName())) {
            existing.setName(name.trim());
            changed = true;
        }
        if (hasText(email) && !email.trim().equalsIgnoreCase(existing.getEmail() == null ? "" : existing.getEmail())) {
            existing.setEmail(email.trim());
            changed = true;
        }
        return changed ? customerRepo.save(existing) : existing;
    }
}
