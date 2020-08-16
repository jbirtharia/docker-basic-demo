package com.docker.basic.beans;

import lombok.*;

@Getter
@AllArgsConstructor
public class AuthenticationResponse {

    private String jwtToken;
    private String token_type;
    private Integer expires_in;
}
