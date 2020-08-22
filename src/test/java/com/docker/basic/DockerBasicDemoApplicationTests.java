package com.docker.basic;

import com.docker.basic.beans.Customer;
import com.docker.basic.controllers.CustomerController;
import com.docker.basic.service.CustomerService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@Log4j2
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
@Transactional
class DockerBasicDemoApplicationTests {


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private CustomerService service;


    @Test
    @SneakyThrows
    public void testIntegrationforCallingGetAllApi() {
        mockMvc.perform(get("/customers")
                ).andExpect(jsonPath("$[0].firstName").isString())
                .andExpect(jsonPath("$[0].firstName").value("Sachin"));
    }

    @Test
    @SneakyThrows
    public void getAllCustomersApiTest() {
        MvcResult mvcResult = mockMvc.perform(get("/customers")
                .content(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(status().isOk()).andReturn();
        List<Customer> customers = mapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<List<Customer>>() {});
        log.info("customers : "+customers);
        Assertions.assertEquals(customers.get(0).getEmail(),getCustomer().getEmail());
    }


    @Test
    @SneakyThrows
    public void testForGetAllApiIsOk(){
        Mockito.when(service.getAll()).thenReturn(Arrays.asList(getCustomer()));
        mockMvc.perform(get("/customers")
                .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk());
    }

    @Test
    public void getAllCustomersTest() {
        CustomerController controller = Mockito.mock(CustomerController.class);
        List<Customer> customers = Arrays.asList(getCustomer());
        Mockito.when(controller.getAllCustomers()).thenReturn(customers);
        Assertions.assertNotNull(customers);
        Assertions.assertEquals("Sachin", customers.get(0).getFirstName());
    }

    @Test
    public void getCustomersTestByEmail() {
        CustomerController controller = Mockito.mock(CustomerController.class);
        Customer customer = getCustomer();
        Mockito.when(controller.getCustomerByEmail(customer.getEmail())).thenReturn(customer);
        Assertions.assertNotNull(customer);
        Assertions.assertEquals(1, customer.getId().intValue());
        Assertions.assertEquals("Sachin", customer.getFirstName());
    }

    @Test
    public void getUpdateCustomerTest() {
        CustomerController controller = Mockito.mock(CustomerController.class);
        Customer oldCustomer = getCustomer();
        oldCustomer.setEmail("abc@gmail.com");
        Customer newCustomer = new Customer(1, "Sachin", "Tendulkar", "abc@gmail.com", passwordEncoder.encode("123"));
        Mockito.when(controller.updateCustomer(newCustomer, oldCustomer.getId())).thenReturn(newCustomer);
        Assertions.assertNotNull(newCustomer);
        Assertions.assertEquals(newCustomer.getId(), oldCustomer.getId());
        Assertions.assertEquals(newCustomer.getFirstName(), oldCustomer.getFirstName());
        Assertions.assertEquals(newCustomer.getLastName(), oldCustomer.getLastName());
        Assertions.assertEquals(newCustomer.getEmail(), oldCustomer.getEmail());
    }

    private Customer getCustomer() {
        return new Customer(1, "Sachin", "Tendulkar", "sachin@gmail.com", passwordEncoder.encode("123"));
    }


}
