package com.docker.basic.repository;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class CustomerRepository {

    @Autowired
    EntityManager entityManager;

    public void getAll() {
        System.out.println("Entity Manager : " + entityManager);
        Session session = entityManager.unwrap(Session.class);
        System.out.println("Session : " + session);
    }
}
