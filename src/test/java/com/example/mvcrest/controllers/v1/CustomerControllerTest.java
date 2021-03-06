package com.example.mvcrest.controllers.v1;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static com.example.mvcrest.controllers.v1.AbstractControllerTest.asJsonString;

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
import com.example.mvcrest.controllers.RestResponseEntityExceptionHandler;
import com.example.mvcrest.services.CustomerService;
import com.example.mvcrest.services.ResourceNotFoundException;

import javassist.NotFoundException;

public class CustomerControllerTest {
	
	CustomerController customerController;
	RestResponseEntityExceptionHandler restResponseEntityExceptionHandler;
	MockMvc mockMvc;
	
	@Mock
	CustomerService customerService;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		restResponseEntityExceptionHandler = new RestResponseEntityExceptionHandler();
		customerController = new CustomerController(customerService);
		
		mockMvc = MockMvcBuilders.standaloneSetup(customerController)
				.setControllerAdvice(restResponseEntityExceptionHandler)
				.build();
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
		
		mockMvc.perform(get(CustomerController.CUSTOMER_BASE_URL)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON))
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
		
		mockMvc.perform(get(CustomerController.CUSTOMER_BASE_URL + "/" + id)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.id", equalTo(id.intValue())));
	}
	
	@Test
	public void testCreateNewCustomer() throws Exception {
		CustomerDTO customer = new CustomerDTO();
		customer.setFirstname("first");
		customer.setLastname("last");
		customer.setId(2L);
		customer.setCustomerUrl(CustomerController.CUSTOMER_BASE_URL + "/2");
		
		when(customerService.createNewCustomer(any(CustomerDTO.class))).thenReturn(customer);
		
		mockMvc.perform(post(CustomerController.CUSTOMER_BASE_URL)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(customer)))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.firstname", equalTo("first")))
			.andExpect(jsonPath("$.customer_url", equalTo("/api/v1/customers/2")));
		
	}
	
	@Test
	public void testUpdateCustomer() throws Exception {
		Long id = 3L;
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setFirstname("foo");
		customerDTO.setLastname("bar");
		customerDTO.setCustomerUrl(CustomerController.CUSTOMER_BASE_URL + "/" + id);
		
		when(customerService.saveCustomerDTO(anyLong(), any(CustomerDTO.class))).thenReturn(customerDTO);
		
		mockMvc.perform(put(CustomerController.CUSTOMER_BASE_URL + "/" + id)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(customerDTO)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.firstname", equalTo("foo")))
			.andExpect(jsonPath("$.customer_url", equalTo(CustomerController.CUSTOMER_BASE_URL + "/" + id)));				
	}
	
	@Test
	public void testPatchCustomer() throws Exception {
		// given
		CustomerDTO newCus = new CustomerDTO();
		String firstname = "firstfoofoo";
		newCus.setFirstname(firstname);
		
		CustomerDTO savedCus = new CustomerDTO();
		Long id = 3L;
		savedCus.setId(id);
		savedCus.setFirstname(firstname);
		savedCus.setLastname("lastbar");
		savedCus.setCustomerUrl(CustomerController.CUSTOMER_BASE_URL + "/" + id);
		
		when(customerService.patchCustomerDTO(anyLong(), any(CustomerDTO.class))).thenReturn(savedCus);
		
		// when
		mockMvc.perform(patch(CustomerController.CUSTOMER_BASE_URL + "/" + id)
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.content(asJsonString(newCus)))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.id", equalTo(id.intValue())))
				.andExpect(jsonPath("$.firstname", equalTo(newCus.getFirstname())))
				.andExpect(jsonPath("$.lastname", equalTo(savedCus.getLastname())))
				.andExpect(jsonPath("$.customer_url", equalTo(CustomerController.CUSTOMER_BASE_URL + "/" + id)));	
	}
	
	@Test
	public void testDeleteById() throws Exception {
		
		mockMvc.perform(delete(CustomerController.CUSTOMER_BASE_URL + "/3")
					.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
		
		verify(customerService, times(1)).deleteCustomerById(new Long(3));
	}
	
	@Test
	public void testResourceNotFoundException() throws Exception {
	
		when(customerService.getById(anyLong())).thenThrow(ResourceNotFoundException.class);
		
		mockMvc.perform(get(CustomerController.CUSTOMER_BASE_URL + "/5000"))
//			.andExpect(content().contentType(MediaType.TEXT_PLAIN_VALUE))
			.andExpect(status().isNotFound());
	}
}
