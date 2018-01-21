package com.example.mvcrest.repositorie;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.mvcrest.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
