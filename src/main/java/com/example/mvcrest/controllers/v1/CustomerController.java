package com.example.mvcrest.controllers.v1;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.mvcrest.api.v1.model.CustomerDTO;
import com.example.mvcrest.api.v1.model.CustomerListDTO;
import com.example.mvcrest.services.CustomerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description="This is value of Cutomer Controller")
@RestController
@RequestMapping(CustomerController.CUSTOMER_BASE_URL)
public class CustomerController {
	
	public static final String CUSTOMER_BASE_URL = "/api/v1/customers";
	
	private final CustomerService customerService;
	
	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}

	@GetMapping("{id}")
	@ResponseStatus(HttpStatus.OK)
	public CustomerDTO getById(@PathVariable String id) {
		
		return customerService.getById(new Long(id));
	}
	
	@ApiOperation(value = "this to get all customers", notes = "These are some notes about the API.")
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public CustomerListDTO getAllCustomers() {
		
		return new CustomerListDTO(customerService.getAllCustomers());
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CustomerDTO createNewCustomer(@RequestBody CustomerDTO customerDTO) {
		
		return customerService.createNewCustomer(customerDTO);
	}
	
	@PutMapping("{id}")
	@ResponseStatus(HttpStatus.OK)
	public CustomerDTO updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
		
		CustomerDTO savedCustomerDTO = customerService.saveCustomerDTO(id, customerDTO);
		
		return savedCustomerDTO;
	}
	
	@PatchMapping("{id}")
	@ResponseStatus(HttpStatus.OK)
	public CustomerDTO patchCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
		
		CustomerDTO savedCustomerDTO = customerService.patchCustomerDTO(id, customerDTO);
		
		return savedCustomerDTO;
	}
	
	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteById(@PathVariable Long id) {
		customerService.deleteCustomerById(id);
	}
}
