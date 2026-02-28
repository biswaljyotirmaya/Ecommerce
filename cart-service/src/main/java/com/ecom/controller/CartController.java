package com.ecom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.dto.CartItemDTO;
import com.ecom.service.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartController {

	@Autowired
	private CartService service;

	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CONSUMER')")
	public String add(@RequestParam Long productId, @RequestParam int qty) {
		Long userId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());

		return service.addToCart(userId, productId, qty);
	}

	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_CONSUMER')")
	public List<CartItemDTO> view() {

		Long userId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());

		return service.getCartItems(userId);
	}

	@DeleteMapping("/clear")
	@PreAuthorize("hasAuthority('ROLE_CONSUMER')")
	public void clear(@RequestHeader("X-USER-ID") String userId) {
		service.clearCart(Long.parseLong(userId));
	}

	@PutMapping("/item")
	@PreAuthorize("hasAuthority('ROLE_CONSUMER')")
	public String updateItem(@RequestParam Long productId, @RequestParam int qty) {

		Long userId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());

		return service.updateItemQuantity(userId, productId, qty);
	}

	@DeleteMapping("/item")
	@PreAuthorize("hasAuthority('ROLE_CONSUMER')")
	public String removeItem(@RequestParam Long productId) {

		Long userId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());

		return service.removeItem(userId, productId);
	}

}
