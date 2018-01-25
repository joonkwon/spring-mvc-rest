package com.example.mvcrest.services;

import java.util.List;

import com.example.mvcrest.api.v1.model.VendorDTO;

public interface VendorService {
	
	VendorDTO getById(Long id);
	List<VendorDTO> getAllVendors();
	VendorDTO saveVendor(VendorDTO vendorDTO);
	VendorDTO updateVendor(Long id, VendorDTO vendorDTO);
	void deleteById(Long id);
}
