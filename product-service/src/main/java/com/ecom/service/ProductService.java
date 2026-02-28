package com.ecom.service;

import java.util.List;
import com.ecom.dto.ProductDTO;

public interface ProductService {

	ProductDTO addProduct(ProductDTO dto);

	ProductDTO updateProduct(Long productId, ProductDTO dto);

	ProductDTO getProductById(Long id);

	List<ProductDTO> getAllProducts();

	List<ProductDTO> getActiveProducts();

	List<ProductDTO> getProductsByCategory(String category);

	List<ProductDTO> getProductsByBrand(String brand);

	List<ProductDTO> getProductsByVendor(Long vendorId);

	void deleteProduct(Long id);

	void reduceStock(Long productId, int quantity);
}