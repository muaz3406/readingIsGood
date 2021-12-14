package com.muaz.readingIsGood.service;

import com.muaz.readingIsGood.dto.CustomerDTO;
import com.muaz.readingIsGood.entity.Customer;
import com.muaz.readingIsGood.exception.BadResourceRequestException;
import com.muaz.readingIsGood.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Value("${pwd.regex}")
    private String pwdRegex;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByUserName(username);
        if (customer == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(customer.getUserName(), customer.getPassword(),
                new ArrayList<>());
    }

    @Transactional
    public Customer createCustomer(CustomerDTO customerDTO) {
        checkIsValidNewCustomer(customerDTO);

        Customer newCustomer = new Customer();
        newCustomer.setUserName(customerDTO.getUserName());
        newCustomer.setPassword(bcryptEncoder.encode(customerDTO.getPassword()));
        return customerRepository.save(newCustomer);
    }

    private void checkIsValidNewCustomer(CustomerDTO customerDTO) {
        if (customerRepository.findByUserName(customerDTO.getUserName()) != null) {
            throw new BadResourceRequestException("userName is already used");
        } else if (!isValid(customerDTO.getPassword())) {
            throw new BadResourceRequestException("Password is not valid");
        }
    }

    public boolean isValid(final String password) {
        Pattern pattern = Pattern.compile(pwdRegex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}