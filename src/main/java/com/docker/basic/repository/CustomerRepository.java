package com.docker.basic.repository;

import com.docker.basic.beans.Customer;
import com.docker.basic.exception.CustomerNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Repository
@Log4j2
public class CustomerRepository {


    @Autowired
    List<Customer> customers;

    public List<Customer> getAll() {
        return customers;
    }

    public Customer save(Customer customer){
        Integer id = this.generateId();
        log.info("New Generated Id : {}",id);
        customer.setId(id);
        customers.add(customer);
        return customer;
    }

    public Customer findOne(Integer id) {
        return customers.stream().filter(customer -> customer.getId().equals(id)).
                findFirst().orElseThrow(()->new CustomerNotFoundException("Customer Not Found !"));
    }

    private Integer generateId(){
        Optional<Customer> customer = customers.stream().max(Comparator.comparingInt(Customer::getId));
        return customer.map(value -> value.getId() + 1).orElse(1);

    }
}
