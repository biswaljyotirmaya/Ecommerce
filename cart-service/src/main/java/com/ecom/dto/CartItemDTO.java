package com.ecom.dto;

import lombok.Data;

@Data
public class CartItemDTO {

	private Long productId;
	private Long vendorId;

	private int quantity;
	private double price;
}
