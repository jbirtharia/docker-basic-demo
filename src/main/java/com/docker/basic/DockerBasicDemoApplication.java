package com.docker.basic;

import com.docker.basic.beans.Customer;
import com.docker.basic.repository.CustomerJPARepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class DockerBasicDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DockerBasicDemoApplication.class, args);
    }


    @Bean
    public CommandLineRunner demo(CustomerJPARepository repository) {
        return args -> {
            repository.save(new Customer(1, "Sachin", "Tendulkar", "sachin@gmail.com","123"));
            repository.save(new Customer(2, "Rahul", "Dravid", "rahul@gmail.com","456"));
        };
    }

}
