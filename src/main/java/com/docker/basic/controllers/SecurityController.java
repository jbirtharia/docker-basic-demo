package com.docker.basic.controllers;

import com.docker.basic.beans.AuthenticationRequest;
import com.docker.basic.beans.AuthenticationResponse;
import com.docker.basic.exception.CredentialException;
import com.docker.basic.security.MyUserDetailService;
import com.docker.basic.util.JwtTokenUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
public class SecurityController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    MyUserDetailService userDetailService;

    @Autowired
    JwtTokenUtil tokenUtil;


    //@PostMapping(value = "/authenticate",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest request){
        log.info("Username : "+request.getEmail() + " Password : "+request.getPassword());
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
        }
        catch (BadCredentialsException exception){
            throw new CredentialException("Username or Password is wrong !!!");
        }
        final UserDetails userDetails =  userDetailService.loadUserByUsername(request.getEmail());
        return ResponseEntity.ok(new AuthenticationResponse(tokenUtil.generateToken(userDetails),"Bearer",
                tokenUtil.extractExpiration(tokenUtil.generateToken(userDetails)).toString())
        );
    }
}
