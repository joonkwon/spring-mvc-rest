package com.example.mvcrest.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CustomerDTO {
	private Long id;
	private String firstname;
	private String lastname;
	
	@JsonProperty("customer_url")
	private String customerUrl;
	
}
