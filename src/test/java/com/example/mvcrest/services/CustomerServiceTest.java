package com.example.mvcrest.services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyLong;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.mvcrest.api.v1.mapper.CustomerMapper;
import com.example.mvcrest.api.v1.model.CustomerDTO;
import com.example.mvcrest.domain.Customer;
import com.example.mvcrest.repositorie.CustomerRepository;

public class CustomerServiceTest {
	
	static final String URL_PREFIX = "/shop/customers/";
	
	CustomerService customerService;
	
	@Mock
	CustomerRepository customerRepository;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		customerService = new CustomerServiceImpl(CustomerMapper.INSTANCE, customerRepository);
	}

	@Test
	public void testGetAllCustomers() {
		// given
		Customer cus1 = new Customer();
		cus1.setId(1L);
		Customer cus2 = new Customer();
		cus2.setId(3L);
		List<Customer> customers = Arrays.asList(cus1, cus2);
		
		when(customerRepository.findAll()).thenReturn(customers);
		
		// when
		List<CustomerDTO> customerList = customerService.getAllCustomers();
		
		// then
		assertEquals(2, customerList.size());
	}
	
	@Test
	public void testGetById() throws Exception {
		// given
		Long id = 3L;
		String firstname = "James";
		String lastname = "Tony";
		
		Customer cus = new Customer();
		cus.setId(id);
		cus.setFirstname(firstname);
		cus.setLastname(lastname);
		
		when(customerRepository.findById(anyLong()))
			.thenReturn(Optional.of(cus));
		
		// when
		CustomerDTO customerDTO = customerService.getById(3L);
		
		// then
		verify(customerRepository, times(1)).findById(anyLong());
		assertEquals(id, customerDTO.getId());
		assertEquals(lastname, customerDTO.getLastname());
		assertEquals(URL_PREFIX + id.toString(), customerDTO.getCustomerUrl());
	}

}