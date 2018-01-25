package com.example.mvcrest.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.mvcrest.domain.Category;
import com.example.mvcrest.domain.Customer;
import com.example.mvcrest.domain.Vendor;
import com.example.mvcrest.repositorie.CategoryRepository;
import com.example.mvcrest.repositorie.CustomerRepository;
import com.example.mvcrest.repositorie.VendorRepository;

@Component
public class Bootstrap implements CommandLineRunner {
	
	private CategoryRepository catRepo;
	private CustomerRepository cusRepo;
	private VendorRepository vendorRepo;

	public Bootstrap(CategoryRepository catRepo,
			CustomerRepository cusRepo, VendorRepository vendorRepo) {
		super();
		this.catRepo = catRepo;
		this.cusRepo = cusRepo;
		this.vendorRepo = vendorRepo;
	}


	@Override
	public void run(String... args) throws Exception {
		
		loadCategories();
		loadCustomers();
		loadVendors();
		
	}
	
	private void loadCategories() {
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
	
	private void loadCustomers() {
		Customer cus1 = new Customer();
		cus1.setFirstname("Joe");
		cus1.setLastname("Newman");
		
		Customer cus2 = new Customer();
		cus2.setFirstname("David");
		cus2.setLastname("Winter");
		    
		Customer cus3 = new Customer();
		cus3.setFirstname("Anne");
		cus3.setLastname("Hine");
		
		Customer cus4 = new Customer();
		cus4.setFirstname("Andrea");
		cus4.setLastname("Il grosso");
		
		cusRepo.save(cus1);
		cusRepo.save(cus2);
		cusRepo.save(cus3);
		cusRepo.save(cus4);
		
		System.out.println("Customers have been created. Count: " + cusRepo.findAll().size());
	}

	private void loadVendors() {
		Vendor vendor1 = new Vendor();
		vendor1.setName("Western Tasty Fruits Ltd.");
		
		Vendor vendor2 = new Vendor();
		vendor2.setName("Home Fruits");
		
		Vendor vendor3 = new Vendor();
		vendor3.setName("Nuts for Nuts Company");
		
		vendorRepo.save(vendor1);
		vendorRepo.save(vendor2);
		vendorRepo.save(vendor3);
		
		System.out.println("Vendors have been created. Count: " + vendorRepo.findAll().size());
		
	}
}