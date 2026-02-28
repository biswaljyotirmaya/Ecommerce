package com.ecom.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Product {

	@Id
	@GeneratedValue
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
