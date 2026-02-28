package com.ecom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.dto.ProductDTO;
import com.ecom.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

	@Autowired
	private ProductService service;

	// Vendor adds product
	@PostMapping
	public ProductDTO add(@RequestBody ProductDTO dto) {

		Long vendorId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());

		dto.setVendorId(vendorId);
		return service.addProduct(dto);
	}

	// Vendor updates own product
	@PutMapping("/{id}")
	public ProductDTO update(@PathVariable Long id, @RequestBody ProductDTO dto) {
		return service.updateProduct(id, dto);
	}

	// Public – all active products
	@GetMapping
	public List<ProductDTO> all() {
		return service.getActiveProducts();
	}

	// Public – product details
	@GetMapping("/{id}")
	public ProductDTO get(@PathVariable Long id) {
		return service.getProductById(id);
	}

	// Vendor/Admin deletes product
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		service.deleteProduct(id);
	}

	// Vendor sees own products
	@GetMapping("/vendor")
	public List<ProductDTO> myProducts() {

		Long vendorId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());

		return service.getProductsByVendor(vendorId);
	}
}
