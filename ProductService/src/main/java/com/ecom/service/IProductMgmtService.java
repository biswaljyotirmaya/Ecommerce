package com.ecom.service;

import java.util.List;

import com.ecom.dto.ProductDto;

public interface IProductMgmtService {

	public String saveProduct(ProductDto dto);
	public List<ProductDto> getAllProducts();
	public ProductDto findProductById(Long id);
}
