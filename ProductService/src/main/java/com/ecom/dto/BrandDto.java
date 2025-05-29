package com.ecom.dto;

import java.util.List;

import com.ecom.entity.Product;

import lombok.Data;

@Data
public class BrandDto {

	private Long id;

	private String name;

	private List<ProductDto> products;
}
