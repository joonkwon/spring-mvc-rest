package com.example.mvcrest.repositorie;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.mvcrest.domain.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{
	
	Category findByName(String name);
}
