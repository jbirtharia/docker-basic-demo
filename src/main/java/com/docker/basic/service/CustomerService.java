package com.docker.basic.service;

import com.docker.basic.beans.Customer;
import com.docker.basic.exception.CustomerNotFoundException;
import com.docker.basic.repository.CustomerJPARepository;
import com.docker.basic.repository.CustomerRepository;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

import static org.apache.commons.beanutils.BeanUtils.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class CustomerService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomerRepository repository;

    @Autowired
    private CustomerJPARepository customerJPARepository;

    public List<Customer> getAll() {
        return customerJPARepository.findAll();
    }

    public Customer save(Customer customer) {
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customerJPARepository.save(customer);
        return customer;
    }

    @SneakyThrows
    public Customer updateCustomer(Integer id, Customer newCustomer) {
        Customer oldCustomer = customerJPARepository.findById(id).orElseThrow(() -> new CustomerNotFoundException("Customer Not Found !"));
        newCustomer.setPassword(passwordEncoder.encode(newCustomer.getPassword()));
        copyProperties(oldCustomer, newCustomer);
        oldCustomer.setId(id);
        customerJPARepository.save(oldCustomer);
        return oldCustomer;
    }

    public Customer findById(Integer id) {
        return customerJPARepository.findById(id).orElseThrow(() -> new CustomerNotFoundException("Customer Not Found !"));
    }

    public Customer findByFirstName(String firstName) {
        return customerJPARepository.findByFirstNameIgnoreCase(firstName).orElseThrow(() -> new CustomerNotFoundException("Customer Not Found !"));
    }

    public Customer findByEmail(String email) {
        return customerJPARepository.findByEmailIgnoreCase(email).orElseThrow(() -> new CustomerNotFoundException("Customer Not Found !"));
    }
}
