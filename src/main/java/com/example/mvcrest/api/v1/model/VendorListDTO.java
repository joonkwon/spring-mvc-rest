package com.example.mvcrest.api.v1.model;

import java.util.List;

import com.example.mvcrest.domain.Vendor;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VendorListDTO {
	private List<VendorDTO> vendors;
}
