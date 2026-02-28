package com.ecom.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import com.ecom.dto.CartItemDTO;

@FeignClient(name = "CART-SERVICE")
public interface CartClient {

	@GetMapping("/api/cart")
	List<CartItemDTO> getCart();

	@DeleteMapping("/api/cart/clear")
	void clear();
}
