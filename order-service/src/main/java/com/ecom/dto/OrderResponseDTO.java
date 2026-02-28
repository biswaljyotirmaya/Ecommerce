package com.ecom.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class OrderResponseDTO {

	private Long orderId;
	private Long userId;
	private Long vendorId;

	private double totalAmount;
	private String status;
	private LocalDateTime createdAt;

	private List<OrderItemResponseDTO> items;
}
