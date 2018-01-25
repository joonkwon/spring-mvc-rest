package com.example.mvcrest.controllers.v1;

import org.hibernate.loader.plan.exec.process.spi.ReturnReader;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.mvcrest.api.v1.model.VendorDTO;
import com.example.mvcrest.api.v1.model.VendorListDTO;
import com.example.mvcrest.services.VendorService;

@Controller
@ResponseBody
@RequestMapping("/api/v1/vendors")
public class VendorController {
	
	private VendorService vendorService;
	
	public VendorController(VendorService vendorService) {
		this.vendorService = vendorService;
	}
	
	@GetMapping
	public VendorListDTO getAll() {
		VendorListDTO vendors = new VendorListDTO(vendorService.getAllVendors());
		return vendors;
	}
	
	@GetMapping("{id}")
	@ResponseStatus(HttpStatus.OK)
	public VendorDTO getById(@PathVariable Long id) {
		return vendorService.getById(id);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public VendorDTO postVendor(@RequestBody VendorDTO vendorDTO) {

		return vendorService.saveVendor(vendorDTO);
	}
	
	@PatchMapping("{id}")
	@ResponseStatus(HttpStatus.OK)
	public VendorDTO patchVendor(@PathVariable Long id, @RequestBody VendorDTO vendorDTO) {
		return vendorService.updateVendor(id, vendorDTO);
	}
	
	@PutMapping("{id}")
	@ResponseStatus(HttpStatus.OK)
	public VendorDTO putVendor(@PathVariable Long id, @RequestBody VendorDTO vendorDTO) {
		return vendorService.updateVendor(id, vendorDTO);
	}
	
	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteVendor(@PathVariable Long id) {
		vendorService.deleteById(id);
	}
}
