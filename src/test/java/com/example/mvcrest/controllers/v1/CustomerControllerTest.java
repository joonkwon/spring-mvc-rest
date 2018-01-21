package com.example.mvcrest.controllers.v1;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.mvcrest.api.v1.model.CustomerDTO;
import com.example.mvcrest.services.CustomerService;

public class CustomerControllerTest {
	
	CustomerController customerController;
	MockMvc mockMvc;
	
	@Mock
	CustomerService customerService;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		customerController = new CustomerController(customerService);
		mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
	}

	@Test
	public void testGetAllCustomers() throws Exception {
		CustomerDTO cus1 = new CustomerDTO();
		cus1.setId(3L);
		CustomerDTO cus2 = new CustomerDTO();
		cus2.setId(7L);
		
		List<CustomerDTO> customers = new ArrayList<>();
		customers.add(cus1);
		customers.add(cus2);
		
		when(customerService.getAllCustomers()).thenReturn(customers);
		
		mockMvc.perform(get("/api/v1/customers"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.customers", hasSize(2)));
	}

	@Test
	public void testGetByID() throws Exception {
		Long id = 3L; 
		CustomerDTO customer = new CustomerDTO();
		customer.setId(id);
		customer.setFirstname("firstname");
		customer.setLastname("lastname");
		
		when(customerService.getById(anyLong())).thenReturn(customer);
		
		mockMvc.perform(get("/api/v1/customers/" + id))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.id", equalTo(id.intValue())));
	}
}
