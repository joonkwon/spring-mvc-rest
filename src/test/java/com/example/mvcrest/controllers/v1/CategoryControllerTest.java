package com.example.mvcrest.controllers.v1;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyString;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.mvcrest.api.v1.model.CategoryDTO;
import com.example.mvcrest.services.CategoryService;

public class CategoryControllerTest {
	
	@InjectMocks
	CategoryController categoryController;
	
	@Mock
	CategoryService categoryService;
	
	MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
	}

	@Test
	public void testListCategories() throws Exception {
		CategoryDTO cat1 = new CategoryDTO();
		cat1.setId(1L);
		cat1.setName("name1");
		
		CategoryDTO cat2 = new CategoryDTO();
		cat2.setId(5L);
		cat2.setName("name5");
		
		List<CategoryDTO> catList = Arrays.asList(cat1, cat2);
		
		when(categoryService.getAllCategories()).thenReturn(catList);
		
		mockMvc.perform(get("/api/v1/categories/")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.categories", hasSize(2)));	
	}

	@Test
	public void testGetCategoryByName() throws Exception {
		String NAME = "some name";
		
		CategoryDTO cat1 = new CategoryDTO();
		cat1.setId(1L);
		cat1.setName(NAME);
		
		when(categoryService.getByName(anyString())).thenReturn(cat1);
		
		mockMvc.perform(get("/api/v1/categories/" + NAME)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.name", equalTo(NAME)));
	}
}
