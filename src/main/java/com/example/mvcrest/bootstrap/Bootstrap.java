package com.example.mvcrest.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.mvcrest.domain.Category;
import com.example.mvcrest.repositorie.CategoryRepository;

@Component
public class Bootstrap implements CommandLineRunner {
	
	private CategoryRepository catRepo;

	public Bootstrap(CategoryRepository catRepo) {
		super();
		this.catRepo = catRepo;
	}


	@Override
	public void run(String... args) throws Exception {
		Category fruits = new Category();
		Category dried = new Category();
		Category fresh = new Category();
		Category exotic = new Category();
		Category nuts = new Category();
		
		fruits.setName("Fruits");
		dried.setName("Dried");
		fresh.setName("Fresh");
		exotic.setName("Exotic");
		nuts.setName("Nuts");
		
		catRepo.save(fruits);
		catRepo.save(dried);
		catRepo.save(fresh);
		catRepo.save(exotic);
		catRepo.save(nuts);
		
		System.out.println("Categories have been created. Count: " + catRepo.count());
	}

}