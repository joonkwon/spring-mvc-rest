package com.example.mvcrest.services;

import java.util.List;

import com.example.mvcrest.api.v1.model.CustomerDTO;

public interface CustomerService {
	
	List<CustomerDTO> getAllCustomers();
	CustomerDTO getById(Long id);
	CustomerDTO createNewCustomer(CustomerDTO customerDTO);
	CustomerDTO saveCustomerDTO(Long id, CustomerDTO customerDTO);
	CustomerDTO patchCustomerDTO(Long id, CustomerDTO customerDTO);
}
