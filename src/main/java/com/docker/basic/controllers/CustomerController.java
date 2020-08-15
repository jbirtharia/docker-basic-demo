package com.docker.basic.controllers;

import com.docker.basic.beans.Customer;
import com.docker.basic.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {

    @Autowired
    CustomerService service;


    @GetMapping("/customers")
    public List<Customer> getAllCustomers() {
        return service.getAll();
    }

    @PostMapping("/customers")
    public Customer saveCustomer(@RequestBody Customer customer) {
        return service.save(customer);
    }

    @GetMapping("/customers/{id}")
    public Customer getCustomerById(@PathVariable Integer id) {
        return service.findById(id);
    }

    @GetMapping("/customers/fname/{firstName}")
    public Customer getCustomerByFirstName(@PathVariable String firstName) {
        return service.findByFirstName(firstName);
    }

    @GetMapping("/customers/email/{email}")
    public Customer getCustomerByEmail(@PathVariable String email) {
        return service.findByEmail(email);
    }
}
