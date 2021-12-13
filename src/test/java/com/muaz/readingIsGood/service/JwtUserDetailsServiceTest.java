package com.muaz.readingIsGood.service;

import com.muaz.readingIsGood.entity.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class JwtUserDetailsServiceTest {

    @InjectMocks
    private JwtUserDetailsService jwtUserDetailsService;
    @Mock
    private CustomerService customerService;
    @Mock
    private PasswordEncoder bcryptEncoder;

    @Test
    public void shouldLoadUserByUsernameWhenFoundUser() {
        Customer customer = new Customer();
        customer.setUserName("muaz");
        customer.setPassword("123");
        when(customerService.findByUserName("muaz")).thenReturn(customer);
        jwtUserDetailsService.loadUserByUsername("muaz");
    }

    @Test(expected = UsernameNotFoundException.class)
    public void shouldThrowExceptionWhenNotFoundCustomer() {
        when(customerService.findByUserName(anyString())).thenReturn(null);
        jwtUserDetailsService.loadUserByUsername("muaz");
    }
}
