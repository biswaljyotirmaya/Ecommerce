package com.ecom.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.dto.OrderResponseDTO;
import com.ecom.entity.Order;
import com.ecom.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

	private final OrderService service;

	public OrderController(OrderService service) {
		this.service = service;
	}

	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CONSUMER')")
	public Order placeOrder() {

		Long userId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());

		return service.placeOrder(userId);
	}

	@GetMapping("/my")
	@PreAuthorize("hasAuthority('ROLE_CONSUMER')")
	public List<OrderResponseDTO> myOrders() {

		Long userId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());

		return service.getOrdersForConsumer(userId);
	}

}
