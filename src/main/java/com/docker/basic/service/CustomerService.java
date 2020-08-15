package com.docker.basic.service;

import com.docker.basic.beans.Customer;
import com.docker.basic.exception.CustomerNotFoundException;
import com.docker.basic.repository.CustomerJPARepository;
import com.docker.basic.repository.CustomerRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class CustomerService {

    @Autowired
    CustomerRepository repository;

    @Autowired
    CustomerJPARepository jpaRepository;

    public List<Customer> getAll() {
        return jpaRepository.findAll();
    }

    public Customer save(Customer customer) {
        jpaRepository.save(customer);
        return customer;
    }

    public Customer findById(Integer id) {
        return jpaRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException("Customer Not Found !"));
    }

    public Customer findByFirstName(String firstName) {
        return jpaRepository.findByFirstNameIgnoreCase(firstName).orElseThrow(() -> new CustomerNotFoundException("Customer Not Found !"));
    }

    public Customer findByEmail(String email) {
        return jpaRepository.findByEmailIgnoreCase(email).orElseThrow(() -> new CustomerNotFoundException("Customer Not Found !"));
    }
}
