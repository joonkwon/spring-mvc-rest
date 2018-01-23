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

import com.example.mvcrest.api.v1.model.CustomerDTO;
import com.example.mvcrest.api.v1.model.CustomerListDTO;
import com.example.mvcrest.services.CustomerService;

@Controller
@RequestMapping(CustomerController.CUSTOMER_BASE_URL)
public class CustomerController {
	
	public static final String CUSTOMER_BASE_URL = "/api/v1/customers";
	
	private final CustomerService customerService;
	
	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}

	@GetMapping("{id}")
	public ResponseEntity<CustomerDTO> getById(@PathVariable String id) {
		
		return new ResponseEntity<CustomerDTO>(
				customerService.getById(new Long(id)), HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<CustomerListDTO> getAllCustomers() {
		
		return new ResponseEntity<CustomerListDTO>(
				new CustomerListDTO(customerService.getAllCustomers()),
				HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<CustomerDTO> createNewCustomer(@RequestBody CustomerDTO customerDTO) {
		
		CustomerDTO savedCustomerDTO = customerService.createNewCustomer(customerDTO);
		return new ResponseEntity<CustomerDTO>(savedCustomerDTO, HttpStatus.CREATED);
	}
	
	@PutMapping("{id}")
	public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
		
		CustomerDTO savedCustomerDTO = customerService.saveCustomerDTO(id, customerDTO);
		
		return new ResponseEntity<CustomerDTO>(savedCustomerDTO, HttpStatus.OK);
	}
	
	@PatchMapping("{id}")
	public ResponseEntity<CustomerDTO> patchCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
		
		CustomerDTO savedCustomerDTO = customerService.patchCustomerDTO(id, customerDTO);
		
		return new ResponseEntity<CustomerDTO>(savedCustomerDTO, HttpStatus.OK);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<Void> deleteById(@PathVariable Long id) {
		customerService.deleteCustomerById(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
