package com.ecom.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.dto.OrderResponseDTO;
import com.ecom.service.OrderService;

@RestController
@RequestMapping("/api/vendor/orders")
public class VendorOrderController {

    private final OrderService orderService;

    public VendorOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // 1️⃣ Vendor sees own orders
    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_VENDOR')")
    public List<OrderResponseDTO> myOrders() {

        Long vendorId = Long.parseLong(
            SecurityContextHolder.getContext().getAuthentication().getName()
        );

        return orderService.getOrdersForVendor(vendorId);
    }

    // 2️⃣ Vendor completes order
    @PutMapping("/{orderId}/complete")
    @PreAuthorize("hasAuthority('ROLE_VENDOR')")
    public String complete(@PathVariable Long orderId) {

        Long vendorId = Long.parseLong(
            SecurityContextHolder.getContext().getAuthentication().getName()
        );

        orderService.completeOrder(orderId, vendorId);
        return "Order marked as COMPLETED";
    }
    
    @GetMapping("/sales")
    @PreAuthorize("hasAuthority('ROLE_VENDOR')")
    public List<OrderResponseDTO> salesHistory() {

        Long vendorId = Long.parseLong(
            SecurityContextHolder.getContext().getAuthentication().getName()
        );

        return orderService.getCompletedSalesForVendor(vendorId);
    }

}
