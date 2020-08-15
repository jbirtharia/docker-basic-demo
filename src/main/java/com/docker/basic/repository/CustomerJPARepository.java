package com.docker.basic.repository;

import com.docker.basic.beans.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerJPARepository extends JpaRepository<Customer, Integer> {

    public Optional<Customer> findByFirstNameIgnoreCase(String firstName);

    public Optional<Customer> findByEmailIgnoreCase(String email);
}
