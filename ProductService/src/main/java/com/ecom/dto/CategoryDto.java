package com.ecom.dto;

import java.util.List;

import com.ecom.entity.Product;

import lombok.Data;

@Data
public class CategoryDto {

	private Long id;

	private String name;

	private String description;

	private List<ProductDto> products;
}
