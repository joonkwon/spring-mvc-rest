package com.example.mvcrest.api.v1.mapper;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.example.mvcrest.api.v1.model.CustomerDTO;
import com.example.mvcrest.domain.Customer;

public class CustomerMapperTest {
	
	CustomerMapper customerMapper;

	@Before
	public void setUp() throws Exception {
		customerMapper = CustomerMapper.INSTANCE;
	}

	@Test
	public void testCustomerToCustomerDTO() {
		Long id = new Long(3L);
		String firstname = "somefirstname";
		String lastname = "somelastname";
		
		Customer customer = new Customer();
		customer.setId(id);
		customer.setFirstname(firstname);
		customer.setLastname(lastname);
		
		CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
		
		assertEquals(id, customerDTO.getId());
		assertEquals(firstname, customerDTO.getFirstname());
		
	}

}
