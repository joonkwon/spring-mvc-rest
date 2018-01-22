package com.example.mvcrest.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.mvcrest.api.v1.mapper.CustomerMapper;
import com.example.mvcrest.api.v1.model.CustomerDTO;
import com.example.mvcrest.domain.Customer;
import com.example.mvcrest.repositorie.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {

	private static final String URL_PREFIX = "/api/v1/customers/";
	private final CustomerRepository customerRepository;
	private final CustomerMapper customerMapper;
	
	public CustomerServiceImpl(CustomerMapper customerMapper, CustomerRepository customerRepository) {
		super();
		this.customerRepository = customerRepository;
		this.customerMapper = customerMapper;
	}

	@Override
	public List<CustomerDTO> getAllCustomers() {
		
		return customerRepository.findAll()
				.stream()
				.map(customer -> {
					CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
					customerDTO.setCustomerUrl(URL_PREFIX + customerDTO.getId());
					return customerDTO;
				})
				.collect(Collectors.toList());
	}

	@Override
	public CustomerDTO getById(Long id) {
		
		CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customerRepository.findById(id).get());
		customerDTO.setCustomerUrl(URL_PREFIX + customerDTO.getId());
		
		return customerDTO;
	}
	
	@Override
	public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {
		
		Customer customer = customerMapper.cutomerDTOToCustomer(customerDTO);
		
		return saveAndReturnDTO(customer);
	}

	private CustomerDTO saveAndReturnDTO(Customer customer) {
		
		Customer savedCustomer = customerRepository.save(customer);
		CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(savedCustomer);
		customerDTO.setCustomerUrl(URL_PREFIX + customerDTO.getId());
		
		return customerDTO;
	}
	
	@Override
	public CustomerDTO saveCustomerDTO(Long id, CustomerDTO customerDTO) {
		
		Customer customer = customerMapper.cutomerDTOToCustomer(customerDTO);
		customer.setId(id);
		
		return saveAndReturnDTO(customer);
	}
	
	@Override
	public CustomerDTO patchCustomerDTO(Long id, CustomerDTO customerDTO) {
		Customer customer = customerRepository.findById(id).get();
		customer.setId(id);
		
		if (customerDTO.getFirstname() != null) {
			customer.setFirstname(customerDTO.getFirstname());
		} 
		if (customerDTO.getLastname() != null) {
			customer.setLastname(customerDTO.getLastname());
		}
		
		return saveAndReturnDTO(customer);
	}

	@Override
	public void deleteCustomerById(Long id) {
		customerRepository.deleteById(id);
	}

}
