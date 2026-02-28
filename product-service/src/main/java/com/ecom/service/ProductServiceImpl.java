package com.ecom.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ecom.dto.ProductDTO;
import com.ecom.entity.Product;
import com.ecom.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository repo;

	private ProductDTO mapToDTO(Product p) {
		ProductDTO dto = new ProductDTO();
		dto.setId(p.getId());
		dto.setName(p.getName());
		dto.setDescription(p.getDescription());
		dto.setPrice(p.getPrice());
		dto.setQuantity(p.getQuantity());
		dto.setCategory(p.getCategory());
		dto.setBrand(p.getBrand());
		dto.setImageUrl(p.getImageUrl());
		dto.setVendorId(p.getVendorId());
		dto.setActive(p.isActive());
		return dto;
	}

	private Product mapToEntity(ProductDTO dto) {
		Product p = new Product();
		p.setName(dto.getName());
		p.setDescription(dto.getDescription());
		p.setPrice(dto.getPrice());
		p.setQuantity(dto.getQuantity());
		p.setCategory(dto.getCategory());
		p.setBrand(dto.getBrand());
		p.setImageUrl(dto.getImageUrl());
		p.setVendorId(dto.getVendorId());
		p.setActive(dto.isActive());
		return p;
	}

	@Override
	public ProductDTO addProduct(ProductDTO dto) {
		Product p = mapToEntity(dto);
		return mapToDTO(repo.save(p));
	}

	@Override
	public ProductDTO updateProduct(Long id, ProductDTO dto) {
		Long vendorId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());

		Product p = repo.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));

		if (!p.getVendorId().equals(vendorId)) {
			throw new AccessDeniedException("You can modify only your own products");
		}

		p.setName(dto.getName());
		p.setDescription(dto.getDescription());
		p.setPrice(dto.getPrice());
		p.setQuantity(dto.getQuantity());
		p.setCategory(dto.getCategory());
		p.setBrand(dto.getBrand());
		p.setImageUrl(dto.getImageUrl());
		p.setActive(dto.isActive());
		return mapToDTO(repo.save(p));
	}

	@Override
	public ProductDTO getProductById(Long id) {
		return mapToDTO(repo.findById(id).orElseThrow());
	}

	@Override
	public List<ProductDTO> getAllProducts() {
		return repo.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
	}

	@Override
	public List<ProductDTO> getActiveProducts() {
		return repo.findByActiveTrue().stream().map(this::mapToDTO).collect(Collectors.toList());
	}

	@Override
	public List<ProductDTO> getProductsByCategory(String category) {
		return repo.findByCategory(category).stream().map(this::mapToDTO).collect(Collectors.toList());
	}

	@Override
	public List<ProductDTO> getProductsByBrand(String brand) {
		return repo.findByBrand(brand).stream().map(this::mapToDTO).collect(Collectors.toList());
	}

	@Override
	public List<ProductDTO> getProductsByVendor(Long vendorId) {
		return repo.findByVendorId(vendorId).stream().map(this::mapToDTO).collect(Collectors.toList());
	}

	@Override
	public void deleteProduct(Long id) {

		Product prod = repo.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));

		String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().iterator().next()
				.getAuthority();

		Long userId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());

		if (role.equals("ROLE_VENDOR") && !prod.getVendorId().equals(userId)) {
			throw new AccessDeniedException("You can delete only your products");
		}

		repo.delete(prod);
	}

	@Override
	public void reduceStock(Long productId, int qty) {
		Product p = repo.findById(productId).orElseThrow();
		p.setQuantity(p.getQuantity() - qty);
		repo.save(p);
	}
}
