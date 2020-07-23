package com.docker.basic;

import com.docker.basic.beans.Customer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class DockerBasicDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DockerBasicDemoApplication.class, args);
	}


	@Bean
	List<Customer> customers(){
		List<Customer> customers = new ArrayList<>();
		customers.add(new Customer(1,"Sachin","Tendulkar","sachin@gmail.com"));
		customers.add(new Customer(2,"Rahul","Dravid","rahul@gmail.com"));
		return customers;
	}
}
