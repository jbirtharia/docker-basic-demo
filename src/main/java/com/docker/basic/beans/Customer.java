package com.docker.basic.beans;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(required = false, hidden = true)
    private Integer id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;
}
