package com.example.mvcrest.api.v1.mapper;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.example.mvcrest.api.v1.model.CategoryDTO;
import com.example.mvcrest.domain.Category;

public class CategoryMapperTest {
	
	private static final Long ID = 1L;
	private static final String NAME = "some name";
	
	CategoryMapper catMapper = CategoryMapper.INSTANCE;
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() throws Exception {
		// given
		Category cat = new Category();
		cat.setId(ID);
		cat.setName(NAME);
		// when
		CategoryDTO categoryDTO = catMapper.categoryToCategoryDTO(cat);
		// then
		assertEquals(ID, categoryDTO.getId());
		assertEquals(NAME, categoryDTO.getName());
		
	}

}
