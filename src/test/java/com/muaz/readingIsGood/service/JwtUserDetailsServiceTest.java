package com.muaz.readingIsGood.service;

import com.muaz.readingIsGood.entity.Customer;
import com.muaz.readingIsGood.repository.CustomerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class JwtUserDetailsServiceTest {

    @InjectMocks
    private JwtUserDetailsService jwtUserDetailsService;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private PasswordEncoder bcryptEncoder;

    @Test
    public void shouldLoadUserByUsernameWhenFoundUser() {
        Customer customer = new Customer();
        customer.setUserName("muaz");
        customer.setPassword("123");
        when(customerRepository.findByUserName("muaz")).thenReturn(customer);
        jwtUserDetailsService.loadUserByUsername("muaz");
    }

    @Test(expected = UsernameNotFoundException.class)
    public void shouldThrowExceptionWhenNotFoundCustomer() {
        when(customerRepository.findByUserName(anyString())).thenReturn(null);
        jwtUserDetailsService.loadUserByUsername("muaz");
    }
}
