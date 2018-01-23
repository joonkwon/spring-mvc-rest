package com.example.mvcrest.services;

import java.util.List;
import java.util.Optional;
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
		Optional <Customer> customerOptional = customerRepository.findById(id);
		if (!customerOptional.isPresent()) {
			throw new ResourceNotFoundException("Customer not found id: " + id);
		}
		CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customerOptional.get());
		customerDTO.setCustomerUrl(URL_PREFIX + customerDTO.getId());
		
		return customerDTO;
	}
	
	@Override
	public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {
		
		return saveAndReturnDTO(customerDTO);
	}

	private CustomerDTO saveAndReturnDTO(CustomerDTO customerDTO) {
		
		Customer savedCustomer = customerRepository.save(customerMapper.cutomerDTOToCustomer(customerDTO));
		CustomerDTO savedCustomerDTO = customerMapper.customerToCustomerDTO(savedCustomer);
		savedCustomerDTO.setCustomerUrl(URL_PREFIX + savedCustomerDTO.getId());
		
		return savedCustomerDTO;
	}
	
	@Override
	public CustomerDTO saveCustomerDTO(Long id, CustomerDTO customerDTO) {
		
		customerDTO.setId(id);
		
		return saveAndReturnDTO(customerDTO);
	}
	
	@Override
	public CustomerDTO patchCustomerDTO(Long id, CustomerDTO customerDTO) {
		CustomerDTO customer = getById(id)	;
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
