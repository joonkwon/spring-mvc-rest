package com.example.mvcrest.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.FactoryBeanNotInitializedException;
import org.springframework.stereotype.Service;

import com.example.mvcrest.api.v1.mapper.CustomerMapper;
import com.example.mvcrest.api.v1.mapper.VendorMapper;
import com.example.mvcrest.api.v1.model.VendorDTO;
import com.example.mvcrest.domain.Vendor;
import com.example.mvcrest.repositorie.VendorRepository;

@Service
public class VendorServiceImpl implements VendorService {
	private static final String URL_PREFIX = "/api/v1/vendors/";
	
	private final VendorRepository vendorRepository;
	private final VendorMapper vendorMapper;
	
	public VendorServiceImpl(VendorRepository vendorRepository, VendorMapper vendorMapper) {
		this.vendorRepository = vendorRepository;
		this.vendorMapper = vendorMapper;
	}

	@Override
	public VendorDTO getById(Long id) {
		Optional<Vendor> vendorOptional = vendorRepository.findById(id);
		if (!vendorOptional.isPresent()) {
			throw new ResourceNotFoundException("Vendor is not found for ID: " + id);
		}
		VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendorOptional.get());
		vendorDTO.setVendorUrl(URL_PREFIX + vendorDTO.getId());
		return vendorDTO;
	}

	@Override
	public List<VendorDTO> getAllVendors() {
		
		return vendorRepository.findAll()
				.stream()
				.map(vendor -> {
					VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);
					vendorDTO.setVendorUrl(URL_PREFIX + vendorDTO.getId());
					return vendorDTO;
				})
				.collect(Collectors.toList());
	}
	
	@Override
	public VendorDTO saveVendor(VendorDTO vendorDTO) {
		Vendor savedVendor = vendorRepository.save(vendorMapper.vendorDTOToVendor(vendorDTO));
		VendorDTO retVendorDTO = vendorMapper.vendorToVendorDTO(savedVendor);
		retVendorDTO.setVendorUrl(URL_PREFIX + retVendorDTO.getId());
		
		return retVendorDTO;
	}
	
	@Override
	public VendorDTO updateVendor(Long id, VendorDTO vendorDTO) {
		VendorDTO oriVendorDTO = getById(id);
		if (oriVendorDTO.getName() != null) {
			oriVendorDTO.setName(vendorDTO.getName());
		}
		VendorDTO retVendorDTO = saveVendor(oriVendorDTO);
		
		return retVendorDTO;
	}
	
	@Override
	public void deleteById(Long id) {
		Optional<Vendor> vendorOptional = vendorRepository.findById(id);
		if (!vendorOptional.isPresent()) {
			throw new ResourceNotFoundException("Vendor doesn't exit with id: " + id);
		}
		vendorRepository.deleteById(id);
	}
}
