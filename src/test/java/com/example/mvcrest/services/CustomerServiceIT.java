package com.example.mvcrest.services;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.mvcrest.api.v1.mapper.CustomerMapper;
import com.example.mvcrest.api.v1.model.CustomerDTO;
import com.example.mvcrest.bootstrap.Bootstrap;
import com.example.mvcrest.domain.Customer;
import com.example.mvcrest.repositorie.CategoryRepository;
import com.example.mvcrest.repositorie.CustomerRepository;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@DataJpaTest
@Slf4j
public class CustomerServiceIT {
	
	CustomerService customerService;
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Before
	public void setUp() throws Exception {
		log.info("Initial customer data count" + customerRepository.findAll().size());
		log.info("loading initial data");
		
		Bootstrap bootstrap = new Bootstrap(categoryRepository, customerRepository);
		bootstrap.run(); // load data
		
		log.info("Initial Data loaded");
		
		customerService = new CustomerServiceImpl(CustomerMapper.INSTANCE, customerRepository);
	}
	
	@Test
	public void testPatchCustomerDTOFirstname() {
		
		// given
		CustomerDTO oldCustomerDTO = customerService.getAllCustomers().get(0);
		
		Long id = oldCustomerDTO.getId();
		String firstname = "firstfoobarfoo";
		
		// when
		CustomerDTO newCus = new CustomerDTO();
		newCus.setFirstname(firstname);
		customerService.patchCustomerDTO(id, newCus);
		
		Customer savedCus = customerRepository.findById(id).get();
		
		// then
		assertNotNull(newCus);
		assertEquals(id, savedCus.getId());
		assertThat(oldCustomerDTO.getFirstname(), not(equalTo(savedCus.getFirstname())));
		assertThat(firstname, equalTo(savedCus.getFirstname()));
		
	}
	
	@Test
	public void testPatchCustomerDTOLatname() {
		// given
		CustomerDTO oldCustomerDTO = customerService.getAllCustomers().get(0);
		
		Long id = oldCustomerDTO.getId();
		String lastname = "lastbarfoobar";
		
		// when
		CustomerDTO newCus = new CustomerDTO();
		newCus.setLastname(lastname);
		customerService.patchCustomerDTO(id, newCus);
		
		Customer savedCus = customerRepository.findById(id).get();
		
		// then
		assertNotNull(newCus);
		assertEquals(id, savedCus.getId());
		assertThat(oldCustomerDTO.getLastname(), not(equalTo(savedCus.getLastname())));
		assertThat(oldCustomerDTO.getFirstname(), equalTo(savedCus.getFirstname()));
	}
	
}
