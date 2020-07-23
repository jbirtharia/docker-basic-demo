package com.docker.basic.beans;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    private Integer id;

    private String firstName;

    private String lastName;

    private String email;
}
