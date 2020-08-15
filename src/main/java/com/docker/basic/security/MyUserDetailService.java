package com.docker.basic.security;

import com.docker.basic.beans.Customer;
import com.docker.basic.exception.CustomerNotFoundException;
import com.docker.basic.repository.CustomerJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    CustomerJPARepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = repository.findByEmailIgnoreCase(username).orElseThrow(() -> new CustomerNotFoundException("Customer Not Found !!!"));
        if(customer != null)
            return new User(customer.getEmail(), customer.getPassword(), new ArrayList<>());
        return null;
    }
}
