package com.ecom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecom.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	List<Product> findByCategory(String category);

	List<Product> findByBrand(String brand);

	List<Product> findByVendorId(Long vendorId);

	List<Product> findByActiveTrue();
}
