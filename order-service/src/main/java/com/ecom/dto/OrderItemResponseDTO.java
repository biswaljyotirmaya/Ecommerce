package com.ecom.dto;

import lombok.Data;

@Data
public class OrderItemResponseDTO {
	private Long productId;
	private int quantity;
	private double price;
}
