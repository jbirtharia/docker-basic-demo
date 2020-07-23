package com.docker.basic.exception;

public class CustomerNotFoundException extends RuntimeException{

    public CustomerNotFoundException(String message) {
        super(message);
    }
}
