package com.ecom.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemDTO {

	private Long productId;
	private Long vendorId;

	private int quantity;
	private double price; // snapshot price
}
