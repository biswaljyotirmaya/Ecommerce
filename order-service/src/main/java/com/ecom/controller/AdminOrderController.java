package com.ecom.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.ecom.service.OrderService;

@RestController
@RequestMapping("/api/admin/orders")
public class AdminOrderController {

	private final OrderService orderService;

	public AdminOrderController(OrderService orderService) {
		this.orderService = orderService;
	}

	// 1️⃣ Cancel all vendor orders
	@DeleteMapping("/vendor/{vendorId}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String cancelVendorOrders(@PathVariable Long vendorId) {

		orderService.cancelOrdersForVendor(vendorId);
		return "Vendor orders cancelled";
	}

	@DeleteMapping("/consumer/{userId}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String cancelConsumerOrders(@PathVariable Long userId) {

		orderService.cancelOrdersForConsumer(userId);
		return "Consumer orders cancelled";
	}

}
