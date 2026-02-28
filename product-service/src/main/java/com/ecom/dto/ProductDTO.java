package com.ecom.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {

	private Long id;
	private String name;
	private String description;
	private double price;
	private int quantity;
	private String category;
	private String brand;
	private String imageUrl;
	private Long vendorId;
	private boolean active;

}
