package com.example.mvcrest.controllers.v1;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.mvcrest.api.v1.model.CategoryDTO;
import com.example.mvcrest.api.v1.model.CategoryListDTO;
import com.example.mvcrest.services.CategoryService;

@Controller
@RequestMapping("/api/v1/categories")
public class CategoryController {

	private final CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		super();
		this.categoryService = categoryService;
	}
	
	@GetMapping
	public ResponseEntity<CategoryListDTO> getAllCategories() {		
		
		CategoryListDTO categories = new CategoryListDTO(categoryService.getAllCategories());
		
		return new ResponseEntity<CategoryListDTO>(categories, HttpStatus.OK);
	}
	
	@GetMapping("{name}")
	public ResponseEntity<CategoryDTO> getCategoryByName(@PathVariable String name) {
		CategoryDTO categoryDTO = categoryService.getByName(name);
		
		return new ResponseEntity<CategoryDTO>(categoryDTO, HttpStatus.OK);
	}
}
