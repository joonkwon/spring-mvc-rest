package com.example.mvcrest.controllers.v1;

import com.example.mvcrest.api.v1.model.CustomerDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractControllerTest {
	
	public static String asJsonString(CustomerDTO customerDTO) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.writeValueAsString(customerDTO);
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

}
