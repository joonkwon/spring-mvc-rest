package com.example.mvcrest.services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyString;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.mvcrest.api.v1.mapper.CategoryMapper;
import com.example.mvcrest.api.v1.model.CategoryDTO;
import com.example.mvcrest.domain.Category;
import com.example.mvcrest.repositorie.CategoryRepository;


public class CategoryServiceTest {
	
	CategoryService catService;
	
	@Mock
	CategoryRepository catRepo;

	@Before
	public void setUp() throws Exception {
		
		MockitoAnnotations.initMocks(this);
		catService = new CategoryServiceImpl(CategoryMapper.INSTANCE, catRepo); 
	}

	@Test
	public void getAllCategoriesTest() {
		// given
		List<Category> categories = new ArrayList<>();
		
		Category cat1 = new Category();
		cat1.setId(3L);
		cat1.setName("this is name one");
		
		Category cat2 = new Category();
		cat2.setId(5L);
		cat2.setName("this is name two");
		
		categories.add(cat1);
		categories.add(cat2);
		
		// when
		when(catRepo.findAll()).thenReturn(categories);
		List<CategoryDTO> categoryDtoList = catService.getAllCategories();
		
		// then
		verify(catRepo, times(1)).findAll();
		assertEquals(2, categoryDtoList.size());
	}

	@Test
	public void getByNameTest() throws Exception {
		// given
		Category cat = new Category();
		String NAME = "this is name one";
		cat.setId(3L);
		cat.setName(NAME);
		
		when(catRepo.findByName(anyString())).thenReturn(cat);
		
		// when
		CategoryDTO categoryDTO = catService.getByName(NAME);
		
		// then
		verify(catRepo, times(1)).findByName(anyString());
		assertEquals(NAME, categoryDTO.getName());
	}
}
