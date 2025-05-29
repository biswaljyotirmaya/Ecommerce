package com.ecom.dto;



import com.ecom.entity.Brand;
import com.ecom.entity.Category;

import lombok.Data;



@Data
public class ProductDto {

	private Long id;
	private String name;
	private String description;
	private Long price;
	private Long stock_Quantity;
	private CategoryDto category;
	private BrandDto brand;

}
