package com.docker.basic.beans;

import lombok.*;

@Data
public class AuthenticationRequest {

    private String email;

    private String password;
}
