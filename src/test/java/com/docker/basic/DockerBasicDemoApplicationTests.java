package com.docker.basic;

import com.docker.basic.beans.Customer;
import com.docker.basic.controllers.CustomerController;
import com.docker.basic.exception.CustomerNotFoundException;
import com.docker.basic.service.CustomerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
class DockerBasicDemoApplicationTests {


	@Autowired
	private PasswordEncoder passwordEncoder;

	@Test
	public void getAllCustomersTest() {
		CustomerController controller = Mockito.mock(CustomerController.class);
		List<Customer> customers = Arrays.asList(getCustomer());
		Mockito.when(controller.getAllCustomers()).thenReturn(customers);
		Assertions.assertNotNull(customers);
		Assertions.assertEquals("Sachin",customers.get(0).getFirstName());
	}

	@Test
	public void getCustomersTestByEmail() {
		CustomerController controller = Mockito.mock(CustomerController.class);
		Customer customer = getCustomer();
		Mockito.when(controller.getCustomerByEmail(customer.getEmail())).thenReturn(customer);
		Assertions.assertNotNull(customer);
		Assertions.assertEquals(1,customer.getId().intValue());
		Assertions.assertEquals("Sachin",customer.getFirstName());
	}

	@Test
	public void getUpdateCustomerTest() {
		CustomerController controller = Mockito.mock(CustomerController.class);
		Customer oldCustomer = getCustomer();
		oldCustomer.setEmail("abc@gmail.com");
		Customer newCustomer = new Customer(1, "Sachin", "Tendulkar", "abc@gmail.com",passwordEncoder.encode("123"));
		Mockito.when(controller.updateCustomer(newCustomer,oldCustomer.getId())).thenReturn(newCustomer);
		Assertions.assertNotNull(newCustomer);
		Assertions.assertEquals(newCustomer.getId(),oldCustomer.getId());
		Assertions.assertEquals(newCustomer.getFirstName(),oldCustomer.getFirstName());
		Assertions.assertEquals(newCustomer.getLastName(),oldCustomer.getLastName());
		Assertions.assertEquals(newCustomer.getEmail(),oldCustomer.getEmail());
	}

	private Customer getCustomer(){
		return new Customer(1, "Sachin", "Tendulkar", "sachin@gmail.com",passwordEncoder.encode("123"));
	}

}
