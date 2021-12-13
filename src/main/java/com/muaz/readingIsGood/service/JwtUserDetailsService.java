package com.muaz.readingIsGood.service;

import com.muaz.readingIsGood.dto.CustomerDTO;
import com.muaz.readingIsGood.entity.Customer;
import com.muaz.readingIsGood.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private  PasswordEncoder bcryptEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Customer customer = customerService.findByUserName(username);
		if (customer == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		return new org.springframework.security.core.userdetails.User(customer.getUserName(), customer.getPassword(),
				new ArrayList<>());
	}

	public Customer createCustomer(CustomerDTO customerDTO) {
		Customer newCustomer = new Customer();
		newCustomer.setUserName(customerDTO.getUserName());
		newCustomer.setPassword(bcryptEncoder.encode(customerDTO.getPassword()));
		return customerService.createCustomer(newCustomer);
	}
}