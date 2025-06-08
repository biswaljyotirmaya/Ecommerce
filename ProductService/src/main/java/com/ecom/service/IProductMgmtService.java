package com.ecom.service;

import java.util.List;

import com.ecom.dto.ProductDto;
import com.ecom.entity.Brand;
import com.ecom.entity.Category;

public interface IProductMgmtService {

	public String saveProduct(ProductDto dto);
	public List<ProductDto> getAllProducts();
	public ProductDto findProductById(Long id);
	public List<ProductDto> findByNameAndCategoryaAndBrand(String name,Category category,Brand brand);
}
