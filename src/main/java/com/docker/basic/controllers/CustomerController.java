package com.docker.basic.controllers;

import com.docker.basic.beans.Customer;
import com.docker.basic.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {

    @Autowired
    CustomerRepository repository;

    @GetMapping("/customers")
    public List<Customer> getAllCustomers(){
        return repository.getAll();
    }

    @PostMapping("/customers")
    public Customer saveCustomer(@RequestBody Customer customer){
        return repository.save(customer);
    }

    @GetMapping("/customers/{id}")
    public Customer getCustomer(@PathVariable Integer id){
        return repository.findOne(id);
    }
}
