package com.example.mvcrest.repositorie;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.mvcrest.domain.Vendor;

public interface VendorRepository extends JpaRepository<Vendor, Long> {

}
