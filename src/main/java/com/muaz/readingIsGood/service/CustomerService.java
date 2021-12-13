package com.muaz.readingIsGood.service;

import com.muaz.readingIsGood.entity.Customer;
import com.muaz.readingIsGood.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer findByUserName(String username) {
        return customerRepository.findByUserName(username);
    }

    public Customer createCustomer(Customer newCustomer) {
        return customerRepository.save(newCustomer);
    }
}
