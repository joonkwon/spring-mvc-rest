package com.example.mvcrest.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.mvcrest.api.v1.mapper.CategoryMapper;
import com.example.mvcrest.api.v1.model.CategoryDTO;
import com.example.mvcrest.repositorie.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	private final CategoryMapper categoryMapper;
	private final CategoryRepository categoryRepository;
	
	public CategoryServiceImpl(CategoryMapper categoryMapper, CategoryRepository categoryRepository) {
		super();
		this.categoryMapper = categoryMapper;
		this.categoryRepository = categoryRepository;
	}

	@Override
	public List<CategoryDTO> getAllCategories() {
		
		return categoryRepository
				.findAll()
				.stream()
				.map(categoryMapper::categoryToCategoryDTO)
				.collect(Collectors.toList());	
	}

	@Override
	public CategoryDTO getByName(String name) {
		
		return categoryMapper.categoryToCategoryDTO(categoryRepository.findByName(name));
	}

}
