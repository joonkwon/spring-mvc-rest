package com.example.mvcrest.services;

import java.util.List;

import com.example.mvcrest.api.v1.model.CategoryDTO;

public interface CategoryService {
	List<CategoryDTO> getAllCategories();
	CategoryDTO getByName(String name);
}
