package com.example.mvcrest.services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.Id;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.mvcrest.api.v1.mapper.VendorMapper;
import com.example.mvcrest.api.v1.model.VendorDTO;
import com.example.mvcrest.domain.Vendor;
import com.example.mvcrest.repositorie.VendorRepository;

public class VendorServiceTest {
	
	VendorService vendorService;
	
	@Mock
	VendorRepository vendorRepository;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		vendorService = new VendorServiceImpl(vendorRepository, VendorMapper.INSTANCE);
	}

	@Test
	public void testGetById() {
		Long id = 2L;
		String name = "test name";
		String urlPrefix = "/api/v1/vendors/";
		
		Vendor vendor = new Vendor();
		vendor.setId(id);
		vendor.setName(name);
		when(vendorRepository.findById(anyLong())).thenReturn(Optional.of(vendor));
		
		VendorDTO vendorDTO = vendorService.getById(id);
		
		assertEquals(name, vendorDTO.getName());
		assertEquals(urlPrefix + id, vendorDTO.getVendorUrl());
	}
	
	@Test
	public void testGetAllVendors() {
		Vendor vendor1 = new Vendor();
		Vendor vendor2 = new Vendor();
		List<Vendor> vendors  = new ArrayList<>();
		vendors.add(vendor1);
		vendors.add(vendor2);
		
		when(vendorRepository.findAll()).thenReturn(vendors);
		
		List<VendorDTO> vendorDTOs = vendorService.getAllVendors();
		
		assertEquals(2, vendorDTOs.size());
	}
	
	@Test
	public void testSaveVendor() {
		// given
		String urlPrefix = "/api/v1/vendors/";
		
		String name = "test name";
		Long id = 2L;
		
		VendorDTO vendorDTO = new VendorDTO();
		vendorDTO.setName(name);
		
		Vendor vendor = new Vendor();
		vendor.setId(id);
		vendor.setName(name);
		
		when(vendorRepository.save(any(Vendor.class))).thenReturn(vendor);
		
		// when
		VendorDTO savedVendorDTO = vendorService.saveVendor(vendorDTO);
		
		// then
		assertEquals(id, savedVendorDTO.getId());
		assertEquals(name, savedVendorDTO.getName());
		assertEquals(urlPrefix + id, savedVendorDTO.getVendorUrl());
	}
	
	@Test
	public void testUpdateVendor() {
		// given
		String urlPrefix = "/api/v1/vendors/";
		
		String name = "test name";
		Long id = 2L;
		
		Vendor vendor = new Vendor();
		vendor.setName(name);
		vendor.setId(id);
		
		String newName = "new name";
		VendorDTO newVendorDTO = new VendorDTO();
		newVendorDTO.setName(newName);
		
		Vendor savedVendor = new Vendor();
		savedVendor.setName(newName);
		savedVendor.setId(id);
		
		when(vendorRepository.findById(anyLong())).thenReturn(Optional.of(vendor));
		when(vendorRepository.save(any())).thenReturn(savedVendor);
		
		// when
		VendorDTO retVendorDTO = vendorService.updateVendor(id, newVendorDTO);
		
		// then
		assertEquals(newName, retVendorDTO.getName());
		assertEquals(id, retVendorDTO.getId());
		assertEquals(urlPrefix + id, retVendorDTO.getVendorUrl());
		
	}

}
